package io.github.endx.vsnh;

import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.ITransformationService;
import cpw.mods.modlauncher.api.ITransformer;

import java.util.List;
import java.util.Set;

public final class VsTfcMixinFixService implements ITransformationService {

    @Override
    public String name() {
        return "vs_tfc_mixin_fix";
    }

    @Override
    public void initialize(IEnvironment environment) {}

    @Override
    public void onLoad(IEnvironment env, Set<String> otherServices) {}

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<ITransformer> transformers() {
        return List.of(new VsTfcMixinFixTransformer());
    }
}
