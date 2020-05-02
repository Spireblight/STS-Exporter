package sts_exporter.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import sts_exporter.Exporter;


@SpirePatch(
        clz=SingleCardViewPopup.class,
        method="renderCost"
)
public class EnergyOrbRenderNoText {
    @SpireInsertPatch(
            rloc=34
    )
    public static void Insert(SingleCardViewPopup __instance, SpriteBatch sb) {
        AbstractCard card = (AbstractCard) ReflectionHacks.getPrivate(__instance, __instance.getClass(), "card");
        card.cost = -2;
        Exporter.logger.info("INSERTING COST=-2");
    }
}