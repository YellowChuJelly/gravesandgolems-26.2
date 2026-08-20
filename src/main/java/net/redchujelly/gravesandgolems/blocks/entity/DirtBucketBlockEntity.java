package net.redchujelly.gravesandgolems.blocks.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.TransferPreconditions;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import net.redchujelly.gravesandgolems.menu.DirtBucketMenu;
import net.redchujelly.gravesandgolems.registry.BlockEntityRegistry;
import net.redchujelly.gravesandgolems.registry.ItemRegistry;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class DirtBucketBlockEntity extends BlockEntity implements MenuProvider{
    public static final int COLUMNS = 9;
    public static final int ROWS = 3;
    private int openCount;
    public static final int CONTAINER_SIZE = 27;
    public static final Component DEFAULT_NAME = Component.translatable("gravesandgolems.container.bucket");
    public static final Identifier CONTENTS = Identifier.withDefaultNamespace("inventory");
    private ItemStacksResourceHandler inventory = new ItemStacksResourceHandler(CONTAINER_SIZE){
        @Override
        protected void onContentsChanged(int index, ItemStack previousContents){
            super.onContentsChanged(index, previousContents);
            DirtBucketBlockEntity.this.setChanged();
            if (!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }


        @Override
        public boolean isValid(int index, ItemResource resource) {
            return resource.toStack().is(ItemRegistry.DIRT_BUCKET_PLACEABLES);
        }
    };
//
//    public @Nullable NonNullList<ItemStack> getItemsFromData(){
//        ItemContainerContents container = this.components().get(DataComponents.CONTAINER);
//        if (container != null){
//            return (NonNullList<ItemStack>) container.allItemsCopyStream().toList();
//        }
//        return null;
//    }

    public DirtBucketBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(BlockEntityRegistry.DIRT_BUCKET_BE.get(), worldPosition, blockState);
//        if (this.getItemsFromData() != null) {
//            List<ItemStack> items = this.getItemsFromData().stream().toList();
//            if (!items.isEmpty()){
//                for (int i = 0; i < items.size(); i++){
//                    this.inventory.set(i, ItemResource.of(items.get(i)), items.get(i).count());
//                }
//            }
//        }
    }


//    @Override
//    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
//        setInventoryForDrop();
//    }


    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putChild("inventory", this.inventory);
    }

    @Override
    protected void loadAdditional(ValueInput input){
        super.loadAdditional(input);
        input.child("inventory").ifPresent(inventory::deserialize);
    }

    @Override
    public Component getDisplayName() {
        return DEFAULT_NAME;
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter components) {
        super.applyImplicitComponents(components);
        NonNullList<ItemStack> items = NonNullList.of(ItemStack.EMPTY);
        components.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY).copyInto(items);
        int i = 0;
        for (ItemStack item : items){
            this.inventory.set(i, ItemResource.of(item), item.count());
        }
    }

    public int getContainerSize(){
        return CONTAINER_SIZE;
    }

//    @Override
//    public boolean isEmpty() {
//        return false;
//    }
//
//    public ItemStack getItem(int slot){
//        return this.inventory.getResource(slot).toStack();
//    }
//
//    @Override
//    public ItemStack removeItem(int i, int i1) {
//        return null;
//    }
//
//    @Override
//    public ItemStack removeItemNoUpdate(int i) {
//        return null;
//    }
//
//    @Override
//    public void setItem(int i, ItemStack itemStack) {
//
//    }
//
//    @Override
//    public boolean stillValid(Player player) {
//        return false;
//    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder components) {
        super.collectImplicitComponents(components);
        components.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(this.getItemHandler().copyToList()));

    }

    //
//    public void setInventory(ItemStacksResourceHandler inventory) {
//        this.inventory = inventory;
//    }



//    public void loadFromTag(ValueInput input) {
//        this.inventory = new ItemStacksResourceHandler(CONTAINER_SIZE);
//
//        ContainerHelper.loadAllItems(input, this.inventory.copyToList());
//
//    }

    /* BLOCK ENTITY SYNC METHODS */
    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void setInventoryForDrop(){
        this.setComponents(DataComponentMap.builder().set(DataComponents.CONTAINER, ItemContainerContents.fromItems(inventory.copyToList())).build());
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }



    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new DirtBucketMenu(i, inventory, this.inventory);
    }

    public ItemStacksResourceHandler getItemHandler(){
        return inventory;
    }

//
//    @Override
//    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
//        return new DirtBucketMenu(i, inventory, this.inventory);
//    }

}
