func0(): int {
    {{{{{{{{{{
        return 5;
    }}}}}}}}}}
}

func1(): int {
    {
        if (false) {
            return 0;
        }
    }
    {
        if (true) {
            return 1;
        }
    }
    {
        if (false) {
            return 2;
        } else if (false) {
            {
                {
                    return 3;
                }
            }
        } else {
            if (true) {
                return 4;
            } else {
                return 5;
            }
        }
    }
}

func2() {
    while true {
        {{{{{{{{{{{{
            return
        };};};};};};};};};};};};
    }
}

func3(b: bool): int, int {
    if b {
        {
            return func4(), func5();
        }
    } else {
        i: int, _ = func3(false);
        return i, func4();
    }
}

func4(): int {
    {
        return 0;
    }
}

func5(): int {
    {
        return 1;
    }
}
