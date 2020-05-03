package cyr7.x86.asm;

import java.util.List;

import cyr7.x86.visitor.AbstractASMVisitor;

// https://ftp.gnu.org/old-gnu/Manuals/gas-2.9.1/html_chapter/as_7.html
public class ASMAssemblerDirective implements ASMLine {

    private final String name;
    private final List<String> args;

    public ASMAssemblerDirective(String name, String... args) {
        this.name = name;
        this.args = List.of(args);
    }

    @Override
    public String getIntelAssembly(boolean indented, boolean withComments) {
        return "." + name + " " + String.join(", ", args);
    }

    @Override
    public <R> R accept(AbstractASMVisitor<R> visitor) {
        return null;
    }

}
