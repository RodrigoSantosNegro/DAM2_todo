import context
import io
import unittest
from contextlib import redirect_stdout
from intro import functions


class ImportsTestCase(unittest.TestCase):

    def test_function_exists_and_simple_program_is_correct(self):
        value = functions.a_color()
        self.assertEqual(value, "Amarillo")

        with redirect_stdout(io.StringIO()) as f:
            from intro import simple_program
        console_output = f.getvalue()

        self.assertTrue(console_output.__contains__("Este es el color:"))
        self.assertTrue(console_output.__contains__("Amarillo"))


if __name__ == '__main__':
    unittest.main()
