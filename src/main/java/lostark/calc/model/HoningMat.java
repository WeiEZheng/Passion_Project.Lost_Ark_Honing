package lostark.calc.model;

import java.util.*;
import java.util.stream.Collectors;
import lostark.domain.enumeration.EquipType;
import lostark.domain.enumeration.TierEnum;

public final class HoningMat {

    private static HoningMat instance = new HoningMat();
    private Map<TierEnum, Map<Integer, List<Integer>>> wepMats = new HashMap<>();
    private Map<TierEnum, Map<Integer, List<Integer>>> armMats = new HashMap<>();

    public HoningMat() {
        List<Integer> mat = Arrays.asList(350, 400, 6, 0, 0);
        wepMats.put(TierEnum.Tier1, Map.of(6, mat));
        wepMats.put(TierEnum.Tier1, Map.of(7, mat));
        mat = Arrays.asList(475, 700, 9, 0, 0);
        wepMats.put(TierEnum.Tier1, Map.of(8, mat));
        wepMats.put(TierEnum.Tier1, Map.of(9, mat));
        mat = Arrays.asList(600, 1000, 12, 0, 0);
        wepMats.put(TierEnum.Tier1, Map.of(10, mat));
        wepMats.put(TierEnum.Tier1, Map.of(11, mat));
        mat = Arrays.asList(210, 240, 4, 0, 0);
        armMats.put(TierEnum.Tier1, Map.of(6, mat));
        armMats.put(TierEnum.Tier1, Map.of(7, mat));
        mat = Arrays.asList(285, 420, 6, 0, 0);
        armMats.put(TierEnum.Tier1, Map.of(8, mat));
        armMats.put(TierEnum.Tier1, Map.of(9, mat));
        mat = Arrays.asList(360, 600, 8, 0, 5);
        armMats.put(TierEnum.Tier1, Map.of(10, mat));
        armMats.put(TierEnum.Tier1, Map.of(11, mat));
    }

    public static HoningMat getInstance() {
        return instance;
    }

    public List<Integer> get(EquipType equipType, TierEnum tier, Integer honingLevel, Boolean SHBuff) {
        if (equipType == EquipType.Armor) return SHBuff(armMats.get(tier).get(honingLevel), SHBuff); else return SHBuff(
            wepMats.get(tier).get(honingLevel),
            SHBuff
        );
    }

    private List<Integer> SHBuff(List<Integer> list, Boolean SHBuff) {
        if (SHBuff == true) for (int i = 0; i < list.size(); i++) {
            list.stream().map(h -> h * 8 / 10).collect(Collectors.toList());
        }
        return list;
    }
}
