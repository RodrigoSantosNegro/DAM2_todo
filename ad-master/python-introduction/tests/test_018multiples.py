import context
import io
import unittest
from unittest.mock import patch
from advanced import loops


class MultiplesForTestCase(unittest.TestCase):

    def test_for_multiples_contain_correct_values_within_closed_range(self):
        with patch('sys.stdout', new=io.StringIO()) as fake_out:
            loops.show_multiples()
            self.assertTrue(fake_out.getvalue().__contains__(self.__expected_out()))

    def test_for_multiples_doesnt_exceed_lower_bound(self):
        lower_bound = 23
        with patch('sys.stdout', new=io.StringIO()) as fake_out:
            loops.show_multiples()
            for i in range(lower_bound, lower_bound - 4, -1):
                self.assertFalse(fake_out.getvalue().__contains__(str(i)))

    def test_for_multiples_doesnt_exceed_upper_bound(self):
        upper_bound = 66
        with patch('sys.stdout', new=io.StringIO()) as fake_out:
            loops.show_multiples()
            for i in range(upper_bound, upper_bound + 4, 1):
                self.assertFalse(fake_out.getvalue().__contains__(str(i)))

    def __expected_out(self):
        expected_output = ""
        range_start = 23
        range_end = 66
        i = 0
        for i in range(range_start+1, range_end):
            if (i % 4) == 0:
                expected_output += str(i) + "\n"
        return expected_output


if __name__ == '__main__':
    unittest.main()
