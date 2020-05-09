There are some weird quirks about this package that I wanted to document.

### Register Allocator

* The register allocator adds a return to the end of a function body so that the
    AsmCFGConstructor works
    
### Interference Graph

* The sets passed into `InterferenceGraph` are passed by reference as to follow
    the Appel algorithm
  
### Hacks

1. ASMAbstract has to def RCX, R10, and R11 so that the prologue has some 
registers it can use, and so the spilled temps don't exponentially explode.

2. If a register is used before its def'd, then that line should be removed.
This is the case with things like caller-saved registers

