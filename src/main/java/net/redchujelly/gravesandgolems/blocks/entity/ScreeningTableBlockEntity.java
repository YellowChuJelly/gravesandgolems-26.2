package net.redchujelly.gravesandgolems.blocks.entity;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.redchujelly.gravesandgolems.GravesAndGolems;
import net.redchujelly.gravesandgolems.blocks.ScreeningTableBlock;
import net.redchujelly.gravesandgolems.registry.BlockEntityRegistry;
import net.redchujelly.gravesandgolems.registry.BlockRegistry;
import net.redchujelly.gravesandgolems.registry.ItemRegistry;
import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;

public class ScreeningTableBlockEntity extends BlockEntity {

    private static final int CONTAINER_SIZE = 1;
    private final ContainerData data;
    private int max_progress = 40;
    private int progress = 0;
    private ItemStacksResourceHandler inventory = new ItemStacksResourceHandler(CONTAINER_SIZE) {
        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            super.onContentsChanged(index, previousContents);
            ScreeningTableBlockEntity.this.setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
        @Override
        public boolean isValid(int index, ItemResource resource) {
            return resource.toStack().is(ItemRegistry.SIFTABLES);
        }

    };


    public ScreeningTableBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(BlockEntityRegistry.SCREENING_TABLE_BE.get(), worldPosition, blockState);
        this.data = new ContainerData() {
            @Override
            public int get(int dataID) {
                return switch (dataID){
                    case 0 -> ScreeningTableBlockEntity.this.progress;
                    case 1 -> ScreeningTableBlockEntity.this.max_progress;
                    default -> 0;
                };
            }

            @Override
            public void set(int dataID, int value) {
                switch (dataID){
                    case 0 -> ScreeningTableBlockEntity.this.progress = value;
                    case 1 -> ScreeningTableBlockEntity.this.max_progress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }


    public void drops() {
        SimpleContainer inv = new SimpleContainer(inventory.size());
        for (int i = 0; i < inventory.size(); i++) {
            ItemAccess itemAccess = ItemAccess.forHandlerIndex(inventory, i);
            inv.setItem(i, new ItemStack(itemAccess.getResource().getItem(), itemAccess.getAmount()));
        }
        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (level instanceof ServerLevel serverLevel) {
            long gametime = level.getGameTime();
            if (gametime % 2 == 0) {
                if (this.getContainedItem().is(ItemRegistry.SIFTABLES)){
                    if (!state.getValue(ScreeningTableBlock.SIFTING)){
                        level.setBlock(pos, state.setValue(ScreeningTableBlock.SIFTING, true), 3);
                    }
                    increaseCraftingProgress();
                    if (gametime % 10 == 0) {
                        level.playSound(null, pos, SoundEvents.BRUSH_SAND, SoundSource.BLOCKS, 0.6f, 1.0f);
                    }
                    setChanged(level, pos, state);

                    if (hasCraftingFinished()){
                        craftItem(getContainedItem(), serverLevel, pos);
                        resetProgress();
                    }
                }
                else {
                    if (state.getValue(ScreeningTableBlock.SIFTING)){
                        level.setBlock(pos, state.setValue(ScreeningTableBlock.SIFTING, false), 3);
                    }
                    resetProgress();
                }
            }
        }
    }

    public void playerUseNoItem(Player player){
        ItemStack BEItem = getContainedItem();
        int shrinkAmount = BEItem.count();
        if (!player.addItem(BEItem)){
            player.drop(BEItem, false);
        }
        BEItem.shrink(shrinkAmount);
    }

    public void playerUseItemStack(Player player, ItemStack playerStack, InteractionHand hand){
        ItemStack BEStack = this.getContainedItem();
        if (ItemStack.isSameItemSameComponents(playerStack, BEStack)){
            if(playerCanFill(playerStack, BEStack)){
                fillFromPlayerStack(playerStack, BEStack);
            } else {
                addAllFromPlayer(playerStack, BEStack);
            }
        } else {
            swapPlayerAndInventoryStacks(player, playerStack, BEStack);
        }
    }

    private void swapPlayerAndInventoryStacks(Player player, ItemStack playerStack, ItemStack beStack) {
        ItemStack playerCopyStack = playerStack.copy();
        ItemStack BECopyStack = playerStack.copy();
        playerStack.shrink(playerStack.count());
        if (!beStack.isEmpty()) {
            if (!player.addItem(beStack)){
                player.drop(beStack, false);
            }
        }
        inventory.set(0, ItemResource.of(playerCopyStack), playerCopyStack.count());

    }

    private void addAllFromPlayer(ItemStack playerStack, ItemStack beStack) {
        int addCount = playerStack.count();
        beStack.grow(addCount);
        playerStack.shrink(addCount);
    }

    private void fillFromPlayerStack(ItemStack playerStack, ItemStack beStack) {
        int addCount = 64 - beStack.count();
        beStack.grow(addCount);
        playerStack.shrink(addCount);
    }

    private boolean playerCanFill(ItemStack playerStack, ItemStack beStack) {
        return playerStack.count() + beStack.count() >= 64;
    }

    private void craftItem(ItemStack item, ServerLevel level, BlockPos pos){
        item.shrink(1);
        ResourceKey<LootTable> outputTable = getRecipeOutputs(item);
        LootTable lootTable = level.getServer().reloadableRegistries().getLootTable(outputTable);
        LootParams params = (new LootParams.Builder(level)).withParameter(LootContextParams.BLOCK_STATE, level.getBlockState(pos)).create(LootContextParamSets.BLOCK_INTERACT);
        ObjectArrayList<ItemStack> loot = lootTable.getRandomItems(params, RandomSource.create());
        ItemStack var10001;

        if (loot.isEmpty()) {
            var10001 = ItemStack.EMPTY;
        } else {
            var10001 = (ItemStack) loot.getFirst();
        }
        Block.popResource(level, this.worldPosition, var10001);
        popResource(level, (Supplier)(() -> new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.6, pos.getZ() + 0.5, var10001, 0, -0.2, 0)), var10001);
    }

    private static void popResource(Level level, Supplier<ItemEntity> entityFactory, ItemStack itemStack) {
        if (level instanceof ServerLevel serverLevel) {
            if (!itemStack.isEmpty() && (Boolean)serverLevel.getGameRules().get(GameRules.BLOCK_DROPS) && !level.restoringBlockSnapshots) {
                ItemEntity entity = (ItemEntity)entityFactory.get();
                entity.setDefaultPickUpDelay();
                level.addFreshEntity(entity);
            }
        }

    }

    private ResourceKey<LootTable> getRecipeOutputs(ItemStack itemStack){
        Item item = itemStack.getItem();
        if (itemStack.is(Tags.Items.SANDS)){
            return ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "sand_sifting_loot_table"));
        } else if (itemStack.is(Tags.Items.GRAVELS)){
            return ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "gravel_sifting_loot_table"));
        } else if (item.equals(Items.MUD)){
            return ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "mud_sifting_loot_table"));
        } else if (item.equals(Items.CLAY)){
            return ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "clay_sifting_loot_table"));
        } else if (item.equals(BlockRegistry.GRAVE_DIRT.get().asItem())){
            return ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "grave_dirt_sifting_loot_table"));
        } else if (item.equals(Items.SOUL_SOIL) || item.equals(Items.SOUL_SAND)){
            return ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "soulsand_sifting_loot_table"));
        }
        return ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "dirt_sifting_loot_table"));
    }

    private boolean hasCraftingFinished(){
        return progress >= max_progress;
    }

    private void increaseCraftingProgress(){
        progress++;
    }

    private void resetProgress(){
        progress = 0;
        max_progress = 100;
    }

    public int getContainerSize(){
        return CONTAINER_SIZE;
    }

    public ItemStack getContainedItem(){
        return this.inventory.copyToList().getFirst();
    }

    public ItemStacksResourceHandler getItemHandler(){
        return inventory;
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("screening_table.progress", this.progress);
        output.putInt("screening_table.max_progress", this.max_progress);
        output.putChild("inventory", this.inventory);
    }

    @Override
    protected void loadAdditional(ValueInput input){
        super.loadAdditional(input);
        input.getIntOr("screening_table.progress", 0);
        input.getIntOr("screening_table.max_progress", 100);
        input.child("inventory").ifPresent(inventory::deserialize);
    }


    /* BLOCK ENTITY SYNC METHODS */
    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public void onDataPacket(Connection net, ValueInput valueInput) {
        super.onDataPacket(net, valueInput);
    }




}
