import context
import unittest
from advanced import loops


class DinoLoopTestCase(unittest.TestCase):

    def test_dino_is_found(self):
        self.assertTrue(loops.search_for_dino("Triceratops"))
        self.assertTrue(loops.search_for_dino("Tiranosaurio"))
        self.assertTrue(loops.search_for_dino("Diplodocus"))
        self.assertTrue(loops.search_for_dino("Velocirraptor"))
        self.assertTrue(loops.search_for_dino("Pterodáctilo"))

    def test_dino_is_not_found(self):
        self.assertFalse(loops.search_for_dino("Homo Habilis"))


if __name__ == '__main__':
    unittest.main()

