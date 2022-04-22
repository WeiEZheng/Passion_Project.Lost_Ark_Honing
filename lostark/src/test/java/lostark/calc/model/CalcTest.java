package lostark.calc.model;

import lostark.domain.enumeration.EquipType;
import lostark.domain.enumeration.MaterialName;
import lostark.domain.enumeration.TierEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalcTest {

    @Test
    void regularCost() {}

    @Test
    void getFusionName() {
        Assertions.assertEquals(Calc.getFusionName(TierEnum.Tier2), MaterialName.CaldarrFusionMaterial);
        Assertions.assertEquals(Calc.getFusionName(TierEnum.Tier3Low), MaterialName.SimpleOrehaFusionMaterial);
        Assertions.assertEquals(Calc.getFusionName(TierEnum.Tier3Mid), MaterialName.BasicOrehaFusionMaterial);
        Assertions.assertEquals(Calc.getFusionName(TierEnum.Tier3High), MaterialName.BasicOrehaFusionMaterial);
    }

    @Test
    void getLeapName() {
        Assertions.assertEquals(Calc.getLeapName(TierEnum.Tier1), MaterialName.HarmonyLeapstone);
        Assertions.assertEquals(Calc.getLeapName(TierEnum.Tier2), MaterialName.LifeLeapstone);
        Assertions.assertEquals(Calc.getLeapName(TierEnum.Tier3Low), MaterialName.HonorLeapstone);
        Assertions.assertEquals(Calc.getLeapName(TierEnum.Tier3Mid), MaterialName.GreatHonorLeapstone);
        Assertions.assertEquals(Calc.getLeapName(TierEnum.Tier3High), MaterialName.GreatHonorLeapstone);
    }

    @Test
    void getShardName() {
        Assertions.assertEquals(Calc.getShardName(TierEnum.Tier1), MaterialName.HarmonyShard);
        Assertions.assertEquals(Calc.getShardName(TierEnum.Tier2), MaterialName.LifeShard);
        Assertions.assertEquals(Calc.getShardName(TierEnum.Tier3Low), MaterialName.HonorShard);
        Assertions.assertEquals(Calc.getShardName(TierEnum.Tier3Mid), MaterialName.HonorShard);
        Assertions.assertEquals(Calc.getShardName(TierEnum.Tier3High), MaterialName.HonorShard);
    }

    @Test
    void getStoneName() {
        Assertions.assertEquals(Calc.getStoneName(TierEnum.Tier1, EquipType.Weapon), MaterialName.DestructionStoneFragment);
        Assertions.assertEquals(Calc.getStoneName(TierEnum.Tier1, EquipType.Armor), MaterialName.GuardianStoneFragment);
    }

    @Test
    void getDestructionName() {
        Assertions.assertEquals(Calc.getDestructionName(TierEnum.Tier1), MaterialName.DestructionStoneFragment);
        Assertions.assertEquals(Calc.getDestructionName(TierEnum.Tier2), MaterialName.DestructionStone);
        Assertions.assertEquals(Calc.getDestructionName(TierEnum.Tier3Low), MaterialName.DestructionStoneCrystal);
        Assertions.assertEquals(Calc.getDestructionName(TierEnum.Tier3Mid), MaterialName.DestructionStoneCrystal);
        Assertions.assertEquals(Calc.getDestructionName(TierEnum.Tier3High), MaterialName.DestructionStoneCrystal);
    }

    @Test
    void getGuardianName() {
        Assertions.assertEquals(Calc.getGuardianName(TierEnum.Tier1), MaterialName.GuardianStoneFragment);
        Assertions.assertEquals(Calc.getGuardianName(TierEnum.Tier2), MaterialName.GuardianStone);
        Assertions.assertEquals(Calc.getGuardianName(TierEnum.Tier3Low), MaterialName.GuardianStoneCrystal);
        Assertions.assertEquals(Calc.getGuardianName(TierEnum.Tier3Mid), MaterialName.GuardianStoneCrystal);
        Assertions.assertEquals(Calc.getGuardianName(TierEnum.Tier3High), MaterialName.GuardianStoneCrystal);
    }

    @Test
    void getCost() {}
}
