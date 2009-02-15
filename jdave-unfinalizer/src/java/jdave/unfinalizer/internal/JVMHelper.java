package jdave.unfinalizer.internal;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

public class JVMHelper {
    public List<VirtualMachineDescriptor> virtualMachines() {
        return VirtualMachine.list();
    }

    public String getCurrentRuntimeName() {
        final RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        final String currentRuntimeName = runtimeMxBean.getName();
        return currentRuntimeName;
    }
}