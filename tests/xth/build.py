import glob
import os
from pathlib import Path

script_file = open(os.path.expanduser(
    '~') + '/shared/cowabunga/tests/xth/xthScriptAll', 'w')


def remove_extension(f):
    return os.path.splitext(f)[0]


def add_xth_block(test_name, cli_arguments, search_pattern):
    files_to_lex = []
    for f in glob.glob(search_pattern, recursive=True):
        dir_name = os.path.dirname(f)
        file_name = os.path.basename(f)
        index_of_dot = file_name.index('.')
        file_name_without_extension = file_name[:index_of_dot]
        files_to_lex.append(os.path.join(
            dir_name, file_name_without_extension))
    # files_to_lex = sorted([remove_extension(f) for f in glob.glob(search_pattern, recursive=True)])
    script_file.write('xic ("' + test_name + '") "' + cli_arguments + '" {\n')

    for f in files_to_lex:
        if os.path.exists(f + '.xi'):
            script_file.write('    ' + f + '.xi;\n')
        elif os.path.exists(f + '.ixi'):
            script_file.write('    ' + f + '.ixi;\n')
        else:
            print('Could not find .xi or .ixi file associated with ' + f)
            exit(1)

    script_file.write('}\n')


if __name__ == '__main__':
    add_xth_block('Cowabunga Lexer Tests', '--lex',
                  './shared/cowabunga/tests/**/*.lexedsol')
    add_xth_block('Cowabunga Parser Tests', '--parse',
                  './shared/cowabunga/tests/**/*.parsedsol')
    add_xth_block('Cowabunga Type-checker Tests', '--typecheck -libpath ./shared/cowabunga/tests/resources/typecheck/lib',
                  './shared/cowabunga/tests/**/*.typedsol')

    add_xth_block('Instructor Lexer Tests', '--lex',
                  './xth/tests/pa1/*.lexedsol')
    add_xth_block('Instructor Parser Tests', '--parse',
                  './xth/tests/pa2/*.parsedsol')
    add_xth_block('Instructor Type-checker Tests',
                  '-libpath ./xth/tests/pa3 --typecheck', './xth/tests/pa3/*.typedsol')
    add_xth_block('Instructor Type-checker Tests',
                  '-libpath ./xth/tests/pa4 --irrun', './xth/tests/pa4/*.irsol.nml')

    script_file.close()
