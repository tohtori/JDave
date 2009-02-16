/*
 * Menu: Derived > Make Targets Derived
 * Kudos: Donnchadh and others
 * License: EPL 1.0
 * DOM: http://download.eclipse.org/technology/dash/update/org.eclipse.eclipsemonkey.lang.javascript
 */

function main() {
  var files = resources.filesMatching(".*/*");  

  for each( file in files ) {
    if (targetFolder = file.eclipseObject.parent.findMember("target")) {
    	targetFolder.setDerived(true);
    }
  }
}