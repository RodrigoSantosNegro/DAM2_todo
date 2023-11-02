from chatbot import ChatBot

if __name__ == "__main__":
    my_object = ChatBot()
    my_object.test_hello()

    my_object.test_one_param(63)
    my_object.test_two_params(22, 14)