import click
import os
from defusedxml import ElementTree as etree
import jinja2

property_file_name = '.property.yml'
property_template_dir = 'hack/templates'
property_template_file_name = 'property_template.jinja'

class Migrator(object):
    def __init__(self, input_dir, output_dir):
        self.input_dir = input_dir
        self.output_dir = output_dir

    def migrate(self):
        for filename in os.listdir(self.input_dir):
            self.migrateItem(filename)

    def migrateItem(self, filename):
        prefix = filename[:-4]
        item_dir = os.path.join(self.output_dir, prefix)
        xml_file = os.path.join(self.input_dir, filename)
        if os.path.exists(item_dir) is False:
            os.makedirs(item_dir)
        # Write config to file
        tree = etree.parse(xml_file)
        with open(os.path.join(item_dir, property_file_name), 'w+') as f:
            f.write(self.render(tree))

    def render(self, tree):
        property_template = jinja2.Environment(
            autoescape=True, loader=jinja2.FileSystemLoader(
                property_template_dir), trim_blocks='true')\
                .get_template(property_template_file_name)
        subcategory = ''
        if tree.find('subcategory') is not None:
            subcategory = self.get_element_text(tree.find('subcategory'))

        description = ''
        if tree.find('description') is not None:
            description = self.get_element_text(tree.find('description')).replace('"', '\\"')

        syntax = ''
        if tree.find('syntax') is not None:
            syntax = etree.tostring(tree.find('syntax'))[9:-12].decode("utf-8")\
            .replace('"', '\\"').replace('\n', '\\n')
            print(str(syntax))

        parameters = []
        for parameter in tree.iterfind('parameter'):
            label = self.get_element_text(parameter.find('label'))
            param_description = ''
            if parameter.find('description') is not None:
                param_description = self.get_element_text(parameter.find('description'))\
                .replace('"', '\\"').replace('\n', ' ')
            parameters.append({'label':label, 'description':param_description})

        # TODO: Support method
        methods = []
        for method in tree.iterfind('method'):
            label = self.get_element_text(method.find('label'))
            param_description = method.find('description')
            # ref = create_ref_link(self.get_element_text(method.find('ref')))
            # self.methods.append({'label':label, 'description':param_description, 'ref':ref})

        constructors = []
        for constructor in tree.iterfind('constructor'):
            constructors.append(self.get_element_text(constructor))

        relateds = []
        for related in tree.iterfind('related'):
            relateds.append(self.get_element_text(related))

        item = {
            'category': self.get_element_text(tree.find('category')),
            'subcategory': subcategory,
            'description': description,
            'syntax': syntax,
            'parameters': parameters,
            'methods': methods,
            'constructors': constructors,
            'related': relateds
            # 'category': self.get_element_text(tree.find('category')),
            # 'category': self.get_element_text(tree.find('category')),
            # 'category': self.get_element_text(tree.find('category')),
            # 'category': self.get_element_text(tree.find('category')),
            # 'category': self.get_element_text(tree.find('category'))
        }
        return property_template.render(item=item)

    def get_element_text(self, element):
        '''Get element.text, supplying the empty string
        if element.text is None. Take filename so we can
        point to errors.'''
        text = element.text
        if text is None:
            print("Warning: Element '{}' has no text".format(element.tag))
            text = ''
        return text

@click.command()
@click.option('--core', default='', help='The location of Processing.R source code.')
@click.option('--py-docs-dir', default='', help='The location of Processing.py reference')
def migrate(core, py_docs_dir):
    '''Migrate From Processing.py to Processing.R web reference.'''
    # BUG: Fail to exit.
    if core is None or py_docs_dir is None:
        click.echo('There is no core or py_docs_dir.')
        exit(1)
    click.echo('The location of Processing.R source code: %s' % core)
    click.echo('The location of Processing.py reference:  %s' % py_docs_dir)

    output_dir_short = 'examples/reference'
    output_dir = os.path.join(core, output_dir_short)
    input_dir_short = 'Reference/api_en'
    input_dir = os.path.join(py_docs_dir, input_dir_short)

    migrator = Migrator(input_dir, output_dir)
    migrator.migrate()

def convert_hypertext(element):
    text = element.text if hasattr(element, 'text') and element.text else ''
    for child in element:
        convert_hypertext(child)
        # We don't just do this at the top level because we need to skip the top-level tags
        s = child
        if s:
            text += s
    return text

if __name__ == '__main__':
    migrate()
