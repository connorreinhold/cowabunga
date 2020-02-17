package cyr7.semantics;

import java.util.Objects;

public class MockType implements Type {

    public final String name;

    public MockType(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MockType mockType = (MockType) o;
        return name.equals(mockType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "MockType{" +
            "name='" + name + '\'' +
            '}';
    }

}
