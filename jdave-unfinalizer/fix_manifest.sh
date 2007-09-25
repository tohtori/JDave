#!/bin/sh
cd target
unzip -o jdave-unfinalizer-1.0-SNAPSHOT.jar META-INF/MANIFEST.MF
sed -i "s/\(Created\)/Premain-Class: jdave.unfinalizer.UnfinalizerInstrumentationLoader\n&/" META-INF/MANIFEST.MF
zip jdave-unfinalizer-1.0-SNAPSHOT.jar META-INF/MANIFEST.MF
cd -
