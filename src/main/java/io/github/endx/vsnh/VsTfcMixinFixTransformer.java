package io.github.endx.vsnh;

import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.ITransformerVotingContext;
import cpw.mods.modlauncher.api.TransformerVoteResult;
import org.objectweb.asm.tree.*;

import java.util.Set;

public final class VsTfcMixinFixTransformer implements ITransformer<ClassNode> {

    private static final String TARGET_MIXIN_CLASS =
            "org.valkyrienskies.mod.forge.mixin.compat.tfc.MixinTFCChunkGenerator";

    private static final String OLD_PRE_GET_BASE_DESC =
            "(IILnet/minecraft/world/level/LevelHeightAccessor;" +
                    "Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V";

    private static final String NEW_PRE_GET_BASE_DESC =
            "(IILnet/minecraft/world/level/LevelHeightAccessor;" +
                    "Lnet/minecraft/world/level/levelgen/RandomState;" +
                    "Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V";

    @Override
    public Set<Target> targets() {
        return Set.of(Target.targetClass(TARGET_MIXIN_CLASS));
    }

    @Override
    public TransformerVoteResult castVote(ITransformerVotingContext context) {
        return TransformerVoteResult.YES;
    }

    @Override
    public ClassNode transform(ClassNode input, ITransformerVotingContext context) {
        if (!TARGET_MIXIN_CLASS.equals(input.name.replace('/', '.'))) return input;

        for (MethodNode m : input.methods) {
            if ("preGetBaseColumn".equals(m.name)) {
                patchPreGetBaseColumnSignature(m);
            }
        }
        return input;
    }

    private static void patchPreGetBaseColumnSignature(MethodNode m) {
        if (!OLD_PRE_GET_BASE_DESC.equals(m.desc)) return; // 已经是新签名就不动

        m.desc = NEW_PRE_GET_BASE_DESC;

        // locals: this=0, i=1, j=2, height=3, (new RandomState)=4, cir=5
        for (AbstractInsnNode insn = m.instructions.getFirst(); insn != null; ) {
            AbstractInsnNode next = insn.getNext();

            if (insn instanceof VarInsnNode vin) {
                if (vin.var >= 4) vin.var += 1;
            } else if (insn instanceof IincInsnNode iinc) {
                if (iinc.var >= 4) iinc.var += 1;
            } else if (insn instanceof FrameNode) {

                m.instructions.remove(insn);
            }

            insn = next;
        }

        if (m.localVariables != null) {
            for (LocalVariableNode lv : m.localVariables) {
                if (lv.index >= 4) lv.index += 1;
            }
        }

        if (m.maxLocals > 0) m.maxLocals += 1;
    }
}
