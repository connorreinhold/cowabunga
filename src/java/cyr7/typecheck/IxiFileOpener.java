package cyr7.typecheck;

import java.io.IOException;
import java.io.Reader;

/**
 * An interface that communicates with the CLI when the contents of an
 * interface file (*.ixi) is requested.
 */
public interface IxiFileOpener {

    /**
     * Retrieves the interface found relative to the directory path of
     * libpath, or the current directory if libpath was not specified in the
     * CLI.
     * 
     * @param name Interface filename, without the extension.
     * @return A Reader of the interface file.
     * @throws IOException If {{@code name}}.ixi cannot be found relative to 
     * the specified libpath (or current directory if not specified) in the CLI.
     */
    Reader openIxiLibraryFile(String name) throws IOException;

}
