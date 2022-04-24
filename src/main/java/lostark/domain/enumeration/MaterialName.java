package lostark.domain.enumeration;

/**
 * The MaterialName enumeration.
 */
public enum MaterialName {
    DestructionStoneFragment("Destruction Stone Fragment"),
    GuardianStoneFragment("Guardian Stone Fragment"),
    HarmonyShard("Harmony Shard"),
    HarmonyLeapstone("Harmony Leapstone"),
    StarsBreaths("Star&#39;s Breaths"),
    DestructionStone("Destruction Stone"),
    GuardianStone("Guardian Stone"),
    LifeShard("Life Shard"),
    LifeLeapstone("Life Leapstone"),
    CaldarrFusionMaterial("Caldarr Fusion Material"),
    MoonsBreaths("Moon&#39;s Breaths"),
    DestructionStoneCrystal("Destruction Stone Crystal"),
    GuardianStoneCrystal("Guardian Stone Crystal"),
    HonorShard("Honor Shard"),
    HonorLeapstone("Honor Leapstone"),
    SimpleOrehaFusionMaterial("Simple Oreha Fusion Material"),
    GreatHonorLeapstone("Great Honor Leapstone"),
    BasicOrehaFusionMaterial("Basic Oreha Fusion Material"),
    SolarGrace("Solar Grace"),
    SolarBlessing("Solar Blessing"),
    SolarProtection("Solar Protection");

    private final String value;

    MaterialName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
