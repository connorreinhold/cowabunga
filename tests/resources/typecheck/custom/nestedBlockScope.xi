main(): int {
    i: int;
    {
        k: int = i
        {
            j: int = i
        }
        {
            j: int = i
        }
    }
    {
        k: int = i
        {
            j: int = i
        }
        {
            j: int = i
        }
    }
    {
        {
            {
                j: int = i
            }
        }
        j: int = i
        {
            k: int = i
        }
    }
    return i
}