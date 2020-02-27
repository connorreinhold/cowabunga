// Should fail typecheck. Variable shares the same name as a function.

main() {
  i: int = 3
}

i() {
}
