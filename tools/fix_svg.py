import re
from typing import List
from os import path
from os import listdir

def fix_file(path: str):
    with open(path, 'r') as file:
        print(path)
        content = file.read()
        classes = re.findall('\\.([sf][a-z0-9]+) {([^}]+)}', content)
        for (class_name, styles) in classes:
            attrs: List[List[str]] = [style.split(':') for style in styles.split(';') if style]
            attrs_str = ' '.join([f'{name}="{value}"' for (name, value) in attrs])
            content = content.replace(f'class="{class_name}"', attrs_str)
    with open(path, 'w') as file:
        file.write(content)

def fix_folder(folder: str):
    for filename in listdir(folder):
        if filename.endswith('.svg'):
            fix_file(path.join(folder, filename))

if __name__ == '__main__':
    fix_folder(path.join('src', 'main', 'resources', 'img'))
