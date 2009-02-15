package jdave.unfinalizer.internal;

import java.util.List;
import java.util.Properties;

import com.sun.tools.attach.VirtualMachineDescriptor;

public class Filter {
    public VirtualMachineDescriptor getCurrentJvm(final JVMHelper jvmHelper) {
        final List<VirtualMachineDescriptor> jvms = jvmHelper.virtualMachines();
        for (final VirtualMachineDescriptor virtualMachineDescriptor : jvms) {
            if (jvmHelper.getCurrentRuntimeName().startsWith(virtualMachineDescriptor.id())) {
                return virtualMachineDescriptor;
            }
        }
        throw new RuntimeException("could not find current virtual machine.");
    }

    public String getUnfinalizerJarPath(final Properties properties) {
        final String classPath = properties.getProperty("java.class.path");
        final String pathSeparator = properties.getProperty("path.separator");
        final String[] classPathItems = classPath.split(pathSeparator);
        for (final String classPathItem : classPathItems) {
            if (classPathItem.matches("(.*)jdave-unfinalizer(.*).jar")) {
                return classPathItem;
            }
        }
        throw new RuntimeException("cannot find jdave unfinalizer in class path");
    }
}