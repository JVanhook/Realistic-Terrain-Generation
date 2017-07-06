package rtg.api.world.deco;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldGenerator;

import net.minecraftforge.event.terraingen.TerrainGen;
import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FLOWERS;

import rtg.api.util.RandomUtil;
import rtg.api.world.IRTGWorld;
import rtg.api.world.biome.IRealisticBiome;
import rtg.api.world.gen.feature.WorldGenFlowersRTG;

/**
 * @author WhichOnesPink
 */
public class DecoFlowersRTG extends DecoBase {

    private int[] flowers; // Integer array of flower IDs.
    private float strengthFactor; // Higher = more flowers.
    private HeightType heightType; // How we determine the Y coord.
    private int chance; // Higher = more rare.
    private int notEqualsZeroChance;

    /*
     * FLOWER LIST:
     * 0	Poppy
     * 1	Blue Orchid
     * 2	Allium
     * 3	Azure Bluet
     * 4	Red Tulip
     * 5	Orange Tulip
     * 6	White Tulip
     * 7	Pink Tulip
     * 8	Oxeye Daisy
     * 9	Yellow Flower
     * 10	Sunflower
     * 11	Lilac
     * 12	Double Tallgrass
     * 13	Large Fern
     * 14	Rose Bush
     * 15	Peony
     */
    public DecoFlowersRTG() {

        super();

        /**
         * Default values.
         * These can be overridden when configuring the Deco object in the realistic biome.
         */
        this.setFlowers(new int[]{0, 9}); // Only roses and dandelions by default.
        this.setChance(1); // 100% chance of generating by default.
        this.setNotEqualsZeroChance(1);
        this.setHeightType(HeightType.NEXT_INT);
        this.setStrengthFactor(0f); // Not sure why it was done like this, but... the higher the value, the more there will be.

        this.addDecoTypes(DecoType.FLOWER);
    }

    @Override
    public void initConfig() {
        this.config().addProperty(this.config().MIN_Y).set(63);
        this.config().addProperty(this.config().MAX_Y).set(253); // 2 below max build height to account for 2-block tall flowers.
        this.config().addProperty(this.config().LOOPS).set(1);
    }

    @Override
    public void generate(IRealisticBiome biome, IRTGWorld rtgWorld, Random rand, int worldX, int worldZ, float strength, float river, boolean hasPlacedVillageBlocks) {

        if (this.config().ALLOW.get()) {

            if (TerrainGen.decorate(rtgWorld.world(), rand, new BlockPos(worldX, 0, worldZ), FLOWERS)) {

                WorldGenerator worldGenerator = new WorldGenFlowersRTG(this.flowers);

                int loops = (this.strengthFactor > 0f) ? (int) (this.strengthFactor * strength) : this.config().LOOPS.get();
                for (int i = 0; i < loops * 16; i++) {
                    int intX = worldX + rand.nextInt(16);// + 8;
                    int intZ = worldZ + rand.nextInt(16);// + 8;

                    int intY;
                    switch (this.heightType) {
                        case NEXT_INT:
                            intY = RandomUtil.getRandomInt(rand, this.config().MIN_Y.get(), this.config().MAX_Y.get());
                            break;

                        case GET_HEIGHT_VALUE:
                            intY = rtgWorld.world().getHeight(new BlockPos(intX, 0, intZ)).getY();
                            break;

                        default:
                            intY = rand.nextInt(this.config().MAX_Y.get());
                            break;

                    }

                    if (this.notEqualsZeroChance > 1) {

                        if (rand.nextInt(this.notEqualsZeroChance) != 0) {

                            worldGenerator.generate(rtgWorld.world(), rand, new BlockPos(intX, intY, intZ));
                        }
                    }
                    else {

                        if (rand.nextInt(this.chance) == 0) {

                            worldGenerator.generate(rtgWorld.world(), rand, new BlockPos(intX, intY, intZ));
                        }
                    }
                }
            }
        }
    }

    public enum HeightType {
        NEXT_INT,
        GET_HEIGHT_VALUE;
    }

    public int[] getFlowers() {

        return flowers;
    }

    public DecoFlowersRTG setFlowers(int[] flowers) {

        this.flowers = flowers;
        return this;
    }

    public float getStrengthFactor() {

        return strengthFactor;
    }

    public DecoFlowersRTG setStrengthFactor(float strengthFactor) {

        this.strengthFactor = strengthFactor;
        return this;
    }

    public HeightType getHeightType() {

        return heightType;
    }

    public DecoFlowersRTG setHeightType(HeightType heightType) {

        this.heightType = heightType;
        return this;
    }

    public int getChance() {

        return chance;
    }

    public DecoFlowersRTG setChance(int chance) {

        this.chance = chance;
        return this;
    }

    public int getNotEqualsZeroChance() {

        return notEqualsZeroChance;
    }

    public DecoFlowersRTG setNotEqualsZeroChance(int notEqualsZeroChance) {

        this.notEqualsZeroChance = notEqualsZeroChance;
        return this;
    }
}