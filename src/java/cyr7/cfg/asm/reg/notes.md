There are some weird quirks about this package that I wanted to document.

### Register Allocator

* The register allocator adds a return to the end of a function body so that the
    AsmCFGConstructor works
    
### Interference Graph

* The sets passed into `InterferenceGraph` are passed by reference as to follow
    the Appel algorithm
  