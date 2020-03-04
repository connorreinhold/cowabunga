package cyr7.ir;

public interface IdGenerator {

    String newLabel();

    String newTemp();

    // Generating a temp for a return type (RV0)
    String retTemp(int num);

    // Generating a temp for a function arg (ARG0)
    String argTemp(int num);

}
