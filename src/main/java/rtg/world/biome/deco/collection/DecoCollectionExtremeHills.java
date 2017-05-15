package rtg.world.biome.deco.collection;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

import rtg.api.util.BlockUtil;
import rtg.api.world.deco.DecoTree;
import rtg.api.world.deco.collection.DecoCollectionBase;
import rtg.api.world.deco.helper.DecoHelper5050;
import rtg.api.world.gen.feature.tree.rtg.TreeRTG;
import rtg.api.world.gen.feature.tree.rtg.TreeRTGPinusNigra;


/**
 * @author WhichOnesPink
 */
public class DecoCollectionExtremeHills extends DecoCollectionBase {

    public DecoCollectionExtremeHills() {

        super();

        this.addDeco(nigraDecos(85, 12, 18, 10, 14)); // Small- to medium-size trees.
    }

    protected DecoTree nigraTrees(IBlockState log, IBlockState leaves, int maxY, int minTrunkSize, int maxTrunkSize, int minCrownSize, int maxCrownSize) {

        TreeRTG nigraTree = new TreeRTGPinusNigra();
        nigraTree.setLogBlock(log);
        nigraTree.setLeavesBlock(leaves);
        nigraTree.setMinTrunkSize(minTrunkSize);
        nigraTree.setMaxTrunkSize(maxTrunkSize);
        nigraTree.setMinCrownSize(minCrownSize);
        nigraTree.setMaxCrownSize(maxCrownSize);

        this.addTree(nigraTree);

        DecoTree nigraDeco = new DecoTree(nigraTree);
        nigraDeco.setStrengthFactorForLoops(3f);
        nigraDeco.setStrengthNoiseFactorXForLoops(true);
        nigraDeco.getDistribution().setNoiseDivisor(100f);
        nigraDeco.getDistribution().setNoiseFactor(6f);
        nigraDeco.getDistribution().setNoiseAddend(0.8f);
        nigraDeco.setTreeType(DecoTree.TreeType.RTG_TREE);
        nigraDeco.setTreeCondition(DecoTree.TreeCondition.RANDOM_CHANCE);
        nigraDeco.setTreeConditionChance(20);
        nigraDeco.setMaxY(maxY);

        return nigraDeco;
    }

    protected DecoHelper5050 nigraDecos(int maxY, int minTrunkSize, int maxTrunkSize, int minCrownSize, int maxCrownSize) {
        return new DecoHelper5050(
            nigraTrees(Blocks.LOG.getDefaultState(), Blocks.LEAVES.getDefaultState(), maxY, minTrunkSize, maxTrunkSize, minCrownSize, maxCrownSize),
            nigraTrees(BlockUtil.getStateLog(1), BlockUtil.getStateLeaf(1), maxY, minTrunkSize, maxTrunkSize, minCrownSize, maxCrownSize)
        );
    }
}
