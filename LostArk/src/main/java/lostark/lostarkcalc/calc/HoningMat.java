package lostark.lostarkcalc.calc;

import lostark.lostarkcalc.domain.enumeration.EquipType;
import lostark.lostarkcalc.domain.enumeration.TierEnum;

import java.util.*;
import java.util.stream.Collectors;

public final class HoningMat {
    private static HoningMat instance = new HoningMat();
    private Map<TierEnum, Map<Integer, List<Integer>>> wepMats = new HashMap<>();
    private Map<TierEnum, Map<Integer, List<Integer>>> armMats = new HashMap<>();

    public HoningMat(){
        List<Integer> mat = Arrays.asList(350, 400, 6, 0);
        wepMats.put(TierEnum.Tier1, Map.of(1, mat));
    }

    public static HoningMat getInstance(){
        return instance;
    }

    public List<Integer> get(EquipType equipType, TierEnum tier, Integer honingLevel, Boolean SHBuff) {
        if (equipType==EquipType.Armor)
            return SHBuff(armMats.get(tier).get(honingLevel), SHBuff);
        else
            return SHBuff(wepMats.get(tier).get(honingLevel), SHBuff);
    }

    private List<Integer> SHBuff (List<Integer> list, Boolean SHBuff){
        if (SHBuff== true)
            for (int i=0; i< list.size();i++){
                list.stream().map(h -> h*8/10).collect(Collectors.toList());
            }
        return list;
    }
}
