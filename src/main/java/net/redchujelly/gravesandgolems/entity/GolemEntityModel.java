package net.redchujelly.gravesandgolems.entity;// Made with Blockbench 4.12.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.redchujelly.gravesandgolems.GravesAndGolems;

public class GolemEntityModel extends EntityModel<LivingEntityRenderState> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(GravesAndGolems.MODID, "golem"), "main");
	private final ModelPart main;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart rightarm;
	private final ModelPart rightleg;
	private final ModelPart leftleg;
	private final ModelPart leftarm;

	public GolemEntityModel(ModelPart root) {
		super(root);
		this.main = root.getChild("main");
		this.body = this.main.getChild("body");
		this.head = this.body.getChild("head");
		this.rightarm = this.body.getChild("rightarm");
		this.rightleg = this.body.getChild("rightleg");
		this.leftleg = this.body.getChild("leftleg");
		this.leftarm = this.body.getChild("leftarm");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 55).addBox(-11.0F, -31.0F, -8.0F, 24.0F, 14.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(0, 143).addBox(-12.0F, -28.0F, -9.0F, 26.0F, 7.0F, 17.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-15.0F, -47.0F, -10.0F, 30.0F, 16.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(2.0F, -48.0F, 0.0F));

		PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 83).addBox(-8.5F, -8.5F, -6.5F, 12.0F, 11.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.5F, -1.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition rightarm = body.addOrReplaceChild("rightarm", CubeListBuilder.create().texOffs(79, 55).addBox(-13.0F, -12.0F, -9.0F, 17.0F, 13.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(113, 80).addBox(-13.0F, 1.0F, -6.0F, 9.0F, 17.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(58, 109).addBox(-12.0F, 18.0F, -5.0F, 8.0F, 14.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-11.0F, -43.0F, 0.0F));

		PartDefinition rightleg = body.addOrReplaceChild("rightleg", CubeListBuilder.create().texOffs(26, 112).addBox(-4.0F, 3.0F, -4.0F, 7.0F, 13.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(26, 132).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, -16.0F, 0.0F));

		PartDefinition leftleg = body.addOrReplaceChild("leftleg", CubeListBuilder.create().texOffs(94, 129).addBox(-4.0F, 8.0F, -4.0F, 7.0F, 9.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(121, 129).addBox(-4.0F, -1.0F, -3.0F, 6.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(9.0F, -17.0F, 0.0F));

		PartDefinition leftarm = body.addOrReplaceChild("leftarm", CubeListBuilder.create().texOffs(96, 23).addBox(-2.0F, -13.0F, -6.0F, 12.0F, 17.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(0, 112).addBox(4.0F, 4.0F, -5.0F, 6.0F, 23.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(94, 109).addBox(1.0F, 27.0F, -7.0F, 9.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(14.0F, -39.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(LivingEntityRenderState state) {
		super.setupAnim(state);

		this.head.xRot = state.xRot * ((float)Math.PI / 180F);
		this.head.yRot = state.yRot * ((float)Math.PI / 180F);

		float walk = state.walkAnimationPos;
		float speed = state.walkAnimationSpeed;
		this.rightleg.xRot = Mth.cos(walk * 0.662F) * 1.4F * speed;
		this.leftleg.xRot = Mth.cos(walk * 0.662F + (float) Math.PI) * 1.4F * speed;
	}

	public ModelPart getHead() {
		return this.head;
	}
}