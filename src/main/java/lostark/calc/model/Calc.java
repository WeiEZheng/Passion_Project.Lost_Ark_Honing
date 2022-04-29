package lostark.calc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lostark.domain.Equipment;
import lostark.domain.MarketPrice;
import lostark.domain.enumeration.EquipType;
import lostark.domain.enumeration.MaterialName;
import lostark.domain.enumeration.TierEnum;
import lostark.repository.MarketPriceRepository;
import lostark.service.MarketPriceService;
import org.springframework.beans.factory.annotation.Autowired;

public class Calc {

    private static HoningMat honingMat = HoningMat.getInstance();
    private static Calc calc = new Calc();

    MarketPrice Destruction;
    MarketPrice StarsBreaths;
    MarketPrice HarmonyShard;
    MarketPrice HarmonyLeapstone;

    @Autowired
    MarketPriceService marketPriceService;

    public static Calc getInstance() {
        return calc;
    }

    public double compareCost(
        Equipment equipment,
        Double basePercent,
        Double additionPercentPerFail,
        Integer failLimit,
        Double maxPercentAfterMats,
        Integer fusionMat1Amount,
        Integer fusionMat2Amount,
        Integer fusionMat3Amount
    ) {
        getMarketPrice();
        Integer cost = RegularCost(equipment);
        Double waitTimeDiff =
            simulate(basePercent, additionPercentPerFail, failLimit, 100000) -
            simulate(maxPercentAfterMats, additionPercentPerFail, failLimit, 100000);
        Integer costOfFusionMat = getExtraCost(equipment, fusionMat1Amount, fusionMat2Amount, fusionMat3Amount);
        return waitTimeDiff * cost - costOfFusionMat;
    }

    private void getMarketPrice() {
        List<MarketPrice> market = marketPriceService.findAll();
        for (MarketPrice s : market) {
            if (s.getItemName() == MaterialName.DestructionStoneFragment) Destruction = s; else if (
                s.getItemName() == MaterialName.StarsBreaths
            ) StarsBreaths = s; else if (s.getItemName() == MaterialName.HarmonyLeapstone) HarmonyLeapstone = s; else if (
                s.getItemName() == MaterialName.HarmonyShard
            ) HarmonyShard = s;
        }
    }

    public Integer getExtraCost(Equipment equipment, Integer fusionMat1Amount, Integer fusionMat2Amount, Integer fusionMat3Amount) {
        if (equipment.getTier() == TierEnum.Tier1) return getCost(
            fusionMat1Amount,
            marketPriceService.findByItemName(MaterialName.StarsBreaths)
        ); else if (equipment.getTier() == TierEnum.Tier2) return getCost(
            fusionMat1Amount,
            marketPriceService.findByItemName(MaterialName.MoonsBreaths)
        ); else return (
            getCost(fusionMat1Amount, marketPriceService.findByItemName(MaterialName.SolarGrace)) +
            getCost(fusionMat2Amount, marketPriceService.findByItemName(MaterialName.SolarBlessing)) +
            getCost(fusionMat3Amount, marketPriceService.findByItemName(MaterialName.SolarProtection))
        );
    }

    public Integer RegularCost(Equipment equipment) {
        List<Integer> mat = honingMat.get(equipment.getEquipmentType(), equipment.getTier(), equipment.getHoningLevel(), false);
        Integer cost = 0;
        cost += getCost(mat.get(0), Destruction);
        cost += getCost(mat.get(1), HarmonyShard);
        cost += getCost(mat.get(2), HarmonyLeapstone);
        if (mat.get(4) != 0) {
            cost += getCost(mat.get(4), marketPriceService.findByItemName(getFusionName(equipment.getTier())));
        }
        cost += mat.get(4);
        return cost;
    }

    public MaterialName getFusionName(TierEnum tier) {
        if (tier == TierEnum.Tier2) return MaterialName.CaldarrFusionMaterial; else if (
            tier == TierEnum.Tier3Low
        ) return MaterialName.SimpleOrehaFusionMaterial; else return MaterialName.BasicOrehaFusionMaterial;
    }

    public MaterialName getLeapName(TierEnum tier) {
        if (tier == TierEnum.Tier1) return MaterialName.HarmonyLeapstone; else if (
            tier == TierEnum.Tier2
        ) return MaterialName.LifeLeapstone; else if (
            tier == TierEnum.Tier3Low
        ) return MaterialName.HonorLeapstone; else return MaterialName.GreatHonorLeapstone;
    }

    public MaterialName getShardName(TierEnum tier) {
        if (tier == TierEnum.Tier1) return MaterialName.HarmonyShard; else if (
            tier == TierEnum.Tier2
        ) return MaterialName.LifeShard; else return MaterialName.HonorShard;
    }

    public MaterialName getStoneName(TierEnum tier, EquipType equipmentType) {
        if (equipmentType == EquipType.Armor) {
            return getGuardianName(tier);
        } else {
            return getDestructionName(tier);
        }
    }

    public MaterialName getDestructionName(TierEnum tier) {
        if (tier == TierEnum.Tier1) return MaterialName.DestructionStoneFragment; else if (
            tier == TierEnum.Tier2
        ) return MaterialName.DestructionStone; else return MaterialName.DestructionStoneCrystal;
    }

    public MaterialName getGuardianName(TierEnum tier) {
        if (tier == TierEnum.Tier1) return MaterialName.GuardianStoneFragment; else if (
            tier == TierEnum.Tier2
        ) return MaterialName.GuardianStone; else return MaterialName.GuardianStoneCrystal;
    }

    public Integer getCost(Integer count, MarketPrice marketPrice) {
        return ((int) Math.ceil(1.0 * count / marketPrice.getNumberPerStack())) * marketPrice.getItemPricePerStack();
    }

    public double simulate(Double basePercent, Double additionPercentPerFail, Integer failLimit, Integer SimCount) {
        Random random = new Random();
        Integer count = 0;
        List<Integer> waitTime = new ArrayList<>();
        while (true) {
            if (random.nextDouble() <= (basePercent + (count * additionPercentPerFail)) || count > failLimit) {
                waitTime.add(count + 1);
                count = 0;
                continue;
            }
            count++;
            if (waitTime.size() >= SimCount) break;
        }
        double avg = 0.0;
        for (int i = 0; i < waitTime.size(); i++) {
            avg += Double.valueOf((waitTime.get(i) - avg) / (i + 1));
        }
        return avg;
    }
}
