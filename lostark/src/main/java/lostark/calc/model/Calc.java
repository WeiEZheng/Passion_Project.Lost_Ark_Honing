package lostark.calc.model;

import java.util.List;
import lostark.domain.Equipment;
import lostark.domain.MarketPrice;
import lostark.domain.enumeration.EquipType;
import lostark.domain.enumeration.MaterialName;
import lostark.domain.enumeration.TierEnum;
import lostark.service.impl.MarketPriceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class Calc {

    private static HoningMat honingMat = HoningMat.getInstance();

    @Autowired
    static MarketPriceServiceImpl marketPriceService;

    public static Integer RegularCost(Equipment equipment) {
        List<Integer> mat = honingMat.get(equipment.getEquipmentType(), equipment.getTier(), equipment.getHoningLevel(), false);
        Integer cost = 0;
        cost +=
            getCost(
                mat.get(0),
                marketPriceService.findOneByItemName(getStoneName(equipment.getTier(), equipment.getEquipmentType())).get()
            );
        cost += getCost(mat.get(1), marketPriceService.findOneByItemName(getShardName(equipment.getTier())).get());
        cost += getCost(mat.get(2), marketPriceService.findOneByItemName(getLeapName(equipment.getTier())).get());
        if (mat.get(4) != 0) {
            cost += getCost(mat.get(4), marketPriceService.findOneByItemName(getFusionName(equipment.getTier())).get());
        }
        cost += mat.get(4);
        return cost;
    }

    public static MaterialName getFusionName(TierEnum tier) {
        if (tier == TierEnum.Tier2) return MaterialName.CaldarrFusionMaterial; else if (
            tier == TierEnum.Tier3Low
        ) return MaterialName.SimpleOrehaFusionMaterial; else return MaterialName.BasicOrehaFusionMaterial;
    }

    public static MaterialName getLeapName(TierEnum tier) {
        if (tier == TierEnum.Tier1) return MaterialName.HarmonyLeapstone; else if (
            tier == TierEnum.Tier2
        ) return MaterialName.LifeLeapstone; else if (
            tier == TierEnum.Tier3Low
        ) return MaterialName.HonorLeapstone; else return MaterialName.GreatHonorLeapstone;
    }

    public static MaterialName getShardName(TierEnum tier) {
        if (tier == TierEnum.Tier1) return MaterialName.HarmonyShard; else if (
            tier == TierEnum.Tier2
        ) return MaterialName.LifeShard; else return MaterialName.HonorShard;
    }

    public static MaterialName getStoneName(TierEnum tier, EquipType equipmentType) {
        if (equipmentType == EquipType.Armor) {
            return getGuardianName(tier);
        } else {
            return getDestructionName(tier);
        }
    }

    public static MaterialName getDestructionName(TierEnum tier) {
        if (tier == TierEnum.Tier1) return MaterialName.DestructionStoneFragment; else if (
            tier == TierEnum.Tier2
        ) return MaterialName.DestructionStone; else return MaterialName.DestructionStoneCrystal;
    }

    public static MaterialName getGuardianName(TierEnum tier) {
        if (tier == TierEnum.Tier1) return MaterialName.GuardianStoneFragment; else if (
            tier == TierEnum.Tier2
        ) return MaterialName.GuardianStone; else return MaterialName.GuardianStoneCrystal;
    }

    public static Integer getCost(Integer count, MarketPrice marketPrice) {
        return ((int) Math.ceil(1.0 * count / marketPrice.getNumberPerStack())) * marketPrice.getItemPricePerStack();
    }
}
