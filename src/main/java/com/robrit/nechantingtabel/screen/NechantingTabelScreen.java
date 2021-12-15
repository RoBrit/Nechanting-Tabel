package com.robrit.nechantingtabel.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.robrit.nechantingtabel.NechantingTabel;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class NechantingTabelScreen extends AbstractContainerScreen<NechantingTabelMenu> {
    private static final ResourceLocation BASE_TEXTURE = new ResourceLocation(NechantingTabel.MOD_ID, "textures/gui/nechanting_tabel.png");

    public NechantingTabelScreen(NechantingTabelMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
        super.renderLabels(poseStack, mouseX, mouseY);

        String levelTotal = String.valueOf(this.menu.getXpLevelCost());
        Component levelText = new TranslatableComponent("container.nechanting_tabel.levels");

        this.font.drawShadow(poseStack, levelTotal, 150 - Math.floorDiv(this.font.width(levelTotal), 2), 34, 0x80FF20);
        this.font.drawShadow(poseStack, levelText, 150 - Math.floorDiv(this.font.width(levelText), 2), 46, 0x80FF20);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BASE_TEXTURE);
        this.blit(poseStack, getGuiLeft(), getGuiTop(), 0, 0, imageWidth, imageHeight);

        if (!this.menu.mayPickup(this.getMinecraft().player, false)) {
            this.blit(poseStack, getGuiLeft() + 66, getGuiTop() + 31, imageWidth, 0, 40, 40);
        }
    }
}
