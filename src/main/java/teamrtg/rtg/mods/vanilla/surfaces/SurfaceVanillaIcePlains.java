package teamrtg.rtg.mods.vanilla.surfaces;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.ChunkPrimer;
import teamrtg.rtg.api.biome.RealisticBiomeBase;
import teamrtg.rtg.util.math.CliffCalculator;
import teamrtg.rtg.util.noise.CellNoise;
import teamrtg.rtg.util.noise.OpenSimplexNoise;
import teamrtg.rtg.world.biome.surface.SurfaceBase;

import java.util.Random;

public class SurfaceVanillaIcePlains extends SurfaceBase {

    public SurfaceVanillaIcePlains(RealisticBiomeBase biome) {
        super(biome);
    }

    @Override
    public void paintSurface(ChunkPrimer primer, int i, int j, int x, int y, int depth, World world, Random rand, OpenSimplexNoise simplex, CellNoise cell, float[] noise, float river, BiomeGenBase[] base) {
        float c = CliffCalculator.calc(x, y, noise);
        boolean cliff = c > 1.4f;

        for (int k = 255; k > -1; k--) {
            Block b = primer.getBlockState(x, k, y).getBlock();
            if (b == Blocks.air) {
                depth = -1;
            } else if (b == Blocks.stone) {
                depth++;

                if (cliff) {
                    if (depth > -1 && depth < 2) {
                        primer.setBlockState(x, k, y, rand.nextInt(3) == 0 ? biome.config.CLIFF_BLOCK_2.get() : biome.config.CLIFF_BLOCK_1.get());
                    } else if (depth < 10) {
                        primer.setBlockState(x, k, y, biome.config.CLIFF_BLOCK_1.get());
                    }
                } else if (depth > -1 && depth < 9) {
                    primer.setBlockState(x, k, y, biome.config.TOP_BLOCK.get());
                }
            }
        }
    }
}
