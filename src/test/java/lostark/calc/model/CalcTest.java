package lostark.calc.model;

import lostark.domain.enumeration.EquipType;
import lostark.domain.enumeration.MaterialName;
import lostark.domain.enumeration.TierEnum;
import lostark.service.Calc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalcTest {

    Calc calc = Calc.getInstance();

    @Test
    void regularCost() {}

    @Test
    void getFusionName() {
        Assertions.assertEquals(calc.getFusionName(TierEnum.Tier2), MaterialName.CaldarrFusionMaterial);
        Assertions.assertEquals(calc.getFusionName(TierEnum.Tier3Low), MaterialName.SimpleOrehaFusionMaterial);
        Assertions.assertEquals(calc.getFusionName(TierEnum.Tier3Mid), MaterialName.BasicOrehaFusionMaterial);
        Assertions.assertEquals(calc.getFusionName(TierEnum.Tier3High), MaterialName.BasicOrehaFusionMaterial);
    }

    @Test
    void getLeapName() {
        Assertions.assertEquals(calc.getLeapName(TierEnum.Tier1), MaterialName.HarmonyLeapstone);
        Assertions.assertEquals(calc.getLeapName(TierEnum.Tier2), MaterialName.LifeLeapstone);
        Assertions.assertEquals(calc.getLeapName(TierEnum.Tier3Low), MaterialName.HonorLeapstone);
        Assertions.assertEquals(calc.getLeapName(TierEnum.Tier3Mid), MaterialName.GreatHonorLeapstone);
        Assertions.assertEquals(calc.getLeapName(TierEnum.Tier3High), MaterialName.GreatHonorLeapstone);
    }

    @Test
    void getShardName() {
        Assertions.assertEquals(calc.getShardName(TierEnum.Tier1), MaterialName.HarmonyShard);
        Assertions.assertEquals(calc.getShardName(TierEnum.Tier2), MaterialName.LifeShard);
        Assertions.assertEquals(calc.getShardName(TierEnum.Tier3Low), MaterialName.HonorShard);
        Assertions.assertEquals(calc.getShardName(TierEnum.Tier3Mid), MaterialName.HonorShard);
        Assertions.assertEquals(calc.getShardName(TierEnum.Tier3High), MaterialName.HonorShard);
    }

    @Test
    void getStoneName() {
        Assertions.assertEquals(calc.getStoneName(TierEnum.Tier1, EquipType.Weapon), MaterialName.DestructionStoneFragment);
        Assertions.assertEquals(calc.getStoneName(TierEnum.Tier1, EquipType.Armor), MaterialName.GuardianStoneFragment);
    }

    @Test
    void getDestructionName() {
        Assertions.assertEquals(calc.getDestructionName(TierEnum.Tier1), MaterialName.DestructionStoneFragment);
        Assertions.assertEquals(calc.getDestructionName(TierEnum.Tier2), MaterialName.DestructionStone);
        Assertions.assertEquals(calc.getDestructionName(TierEnum.Tier3Low), MaterialName.DestructionStoneCrystal);
        Assertions.assertEquals(calc.getDestructionName(TierEnum.Tier3Mid), MaterialName.DestructionStoneCrystal);
        Assertions.assertEquals(calc.getDestructionName(TierEnum.Tier3High), MaterialName.DestructionStoneCrystal);
    }

    @Test
    void getGuardianName() {
        Assertions.assertEquals(calc.getGuardianName(TierEnum.Tier1), MaterialName.GuardianStoneFragment);
        Assertions.assertEquals(calc.getGuardianName(TierEnum.Tier2), MaterialName.GuardianStone);
        Assertions.assertEquals(calc.getGuardianName(TierEnum.Tier3Low), MaterialName.GuardianStoneCrystal);
        Assertions.assertEquals(calc.getGuardianName(TierEnum.Tier3Mid), MaterialName.GuardianStoneCrystal);
        Assertions.assertEquals(calc.getGuardianName(TierEnum.Tier3High), MaterialName.GuardianStoneCrystal);
    }

    @Test
    void getCost() {}

    @Test
    void compareCost() {}

    @Test
    void getExtraCost() {}

    @Test
    void simulate() {}
}
