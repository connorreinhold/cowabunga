package cyr7.typecheck;

import java.io.IOException;
import java.io.Reader;

public interface IxiFileOpener {

    Reader openIxiLibraryFile(String name) throws IOException;

}
