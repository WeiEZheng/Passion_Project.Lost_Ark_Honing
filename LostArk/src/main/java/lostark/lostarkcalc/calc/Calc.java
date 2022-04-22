package lostark.lostarkcalc.calc;

import lostark.lostarkcalc.domain.Equipment;
import lostark.lostarkcalc.domain.Item;
import lostark.lostarkcalc.domain.MarketPrice;
import lostark.lostarkcalc.domain.enumeration.EquipType;
import lostark.lostarkcalc.domain.enumeration.MaterialName;
import lostark.lostarkcalc.domain.enumeration.TierEnum;
import lostark.lostarkcalc.service.impl.ItemServiceImpl;
import lostark.lostarkcalc.service.impl.MarketPriceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Calc {
    private static HoningMat honingMat = HoningMat.getInstance();
    @Autowired
    static ItemServiceImpl itemService;
    @Autowired
    static MarketPriceServiceImpl marketPriceService;
    public static Integer Calc(Equipment equipment){
        List<Integer> mat = honingMat.get(equipment.getEquipmentType(),equipment.getTier(),
            equipment.getHoningLevel(), false);
        Integer cost=0;
        cost += getCost(mat.get(0),
            itemService.findOneByName(getShardName(equipment.getTier(), equipment.getEquipmentType())).get());
        return cost;
    }

    public static MaterialName getShardName(TierEnum tierEnum, EquipType equipmentType) {
        if (equipmentType == EquipType.Armor) {
            if (tierEnum == TierEnum.Tier1)
                return MaterialName.GuardianStoneFragment;
            else if (tierEnum == TierEnum.Tier2)
                return MaterialName.GuardianStone;
            else
                return MaterialName.GuardianStoneCrystal;
        }
        else {
            if (tierEnum == TierEnum.Tier1)
                return MaterialName.DestructionStoneFragment;
            else if (tierEnum == TierEnum.Tier2)
                return MaterialName.DestructionStone;
            else
                return MaterialName.DestructionStoneCrystal;
        }
    }

    public static Integer getCost(Integer count, Item item){
        MarketPrice price = marketPriceService.findOneByItem(item).get();
        Integer cost = ((int) Math.ceil(1.0 * count / price.getNumberPerStack())) * price.getItemPricePerStack();
        return cost;
    }
}
