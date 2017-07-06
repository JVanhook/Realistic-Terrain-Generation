package rtg.api.world.deco;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldGenerator;

import rtg.api.util.RandomUtil;
import rtg.api.world.IRTGWorld;
import rtg.api.world.biome.IRealisticBiome;
import rtg.api.world.gen.feature.WorldGenBlock;

/**
 * @author WhichOnesPink
 */
public class DecoCobwebs extends DecoBase {

    private float strengthFactor; // Higher = more/bigger boulders.
    private int chance; // Higher = more rare.
    private IBlockState adjacentBlock;
    private int minAdjacents;

    public DecoCobwebs() {

        super();

        /**
         * Default values.
         * These can be overridden when configuring the Deco object in the realistic biome.
         */
        this.setStrengthFactor(2f);
        this.setChance(10);
        this.setAdjacentBlock(Blocks.AIR.getDefaultState());
        this.setMinAdjacents(1);

        this.addDecoTypes(DecoType.COBWEB);
    }

    @Override
    public void initConfig() {
        this.config().addProperty(this.config().MIN_Y).set(62);
        this.config().addProperty(this.config().MAX_Y).set(255);
    }

    @Override
    public void generate(IRealisticBiome biome, IRTGWorld rtgWorld, Random rand, int worldX, int worldZ, float strength, float river, boolean hasPlacedVillageBlocks) {

        if (this.config().ALLOW.get()) {

            WorldGenerator worldGenerator = new WorldGenBlock(Blocks.WEB.getDefaultState(), Blocks.AIR.getDefaultState(), this.adjacentBlock, this.minAdjacents);

            for (int l1 = 0; l1 < this.strengthFactor * strength; ++l1) {
                int i1 = worldX + rand.nextInt(16);// + 8;
                int j1 = worldZ + rand.nextInt(16);// + 8;
                int k1 = RandomUtil.getRandomInt(rand, this.config().MIN_Y.get(), this.config().MAX_Y.get());

                if (rand.nextInt(this.chance) == 0) {
                    worldGenerator.generate(rtgWorld.world(), rand, new BlockPos(i1, k1, j1));
                }
            }
        }
    }

    public float getStrengthFactor() {

        return strengthFactor;
    }

    public DecoCobwebs setStrengthFactor(float strengthFactor) {

        this.strengthFactor = strengthFactor;
        return this;
    }

    public int getChance() {

        return chance;
    }

    public DecoCobwebs setChance(int chance) {

        this.chance = chance;
        return this;
    }

    public IBlockState getAdjacentBlock() {

        return adjacentBlock;
    }

    public DecoCobwebs setAdjacentBlock(IBlockState adjacentBlock) {

        this.adjacentBlock = adjacentBlock;
        return this;
    }

    public int getMinAdjacents() {

        return minAdjacents;
    }

    public DecoCobwebs setMinAdjacents(int minAdjacents) {

        this.minAdjacents = minAdjacents;
        return this;
    }
}