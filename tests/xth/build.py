import glob
import os
from pathlib import Path

script_file = open(os.path.expanduser(
    '~') + '/shared/cowabunga/tests/xth/xthScriptAll', 'w')


integration_file = open(os.path.expanduser('~') +
        '/shared/cowabunga/tests/xth/xthScriptIntegration', 'w')


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


def add_integration_block(test_name, cli_arguments):
    files_to_lex = []
    for f in glob.glob('./shared/cowabunga/tests/resources/integration/*.ssol.nml', recursive=True):
        dir_name = os.path.dirname(f)
        file_name = os.path.basename(f)
        index_of_dot = file_name.index('.')
        file_name_without_extension = file_name[:index_of_dot]
        files_to_lex.append(os.path.join(
            dir_name, file_name_without_extension))

    integration_file.write('xic ("' + test_name + '") "' + cli_arguments + '" {\n')

    for f in files_to_lex:
        if os.path.exists(f + '.xi'):
            integration_file.write('    ' + os.path.basename(f) + '.xi;\n')
        else:
            print('Could not find .xi or .ixi file associated with ' + f)
            exit(1)

    integration_file.write('}\n')


if __name__ == '__main__':
#     add_xth_block(
#         'Cowabunga Lexer Tests', '--lex',
#         './shared/cowabunga/tests/**/*.lexedsol')
#     add_xth_block(
#         'Cowabunga Parser Tests',
#         '--parse',
#         './shared/cowabunga/tests/**/*.parsedsol')
#     add_xth_block(
#         'Cowabunga Type-checker Tests',
#         '--typecheck -libpath ./shared/cowabunga/tests/resources/typecheck/lib',
#         './shared/cowabunga/tests/**/*.typedsol')
#     add_xth_block(
#         'Cowabunga IRGen Tests',
#         '--irgen -libpath ./runtime/include',
#         './shared/cowabunga/tests/**/*.irsol.nml')
    add_xth_block(
        'Cowabunga IRGen Tests (Optimization Disabled)',
        '--irgen -libpath ./runtime/include -O',
        './shared/cowabunga/tests/**/*.irsol.nml')

    add_integration_block(
        'Cowabunga ASM Tests',
        '-libpath ./runtime/include')

    script_file.close()
