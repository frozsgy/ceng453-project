
# Level One

The first level has a very simple AI mechanism. In this level, the AI simply throws the cards starting from the leftmost one to the rightmost one. It does not care about having matching cards.

# Level Two

The second level has an improved AI mechanism. In this level, the AI checks the middle stack. 
* If the middle stack is empty, it plays a random card. 
* If there is a card in the stack, it tries to play a matching card. 
* If it cannot find a matching card, it plays a random card.

# Level Three

Since the third level has bluffing, the AI in this level is fairly improved. 
* If there is only one card in the middle stack, with a probability of 33%, the AI will bluff. 
* If the AI decided to bluff, with 50% chance, it tries to make this a real bluff. If a real bluff is not possible, it selects a random card.
* If the AI decided not to bluff, it tries all the cards at its hand to calculate their scores, and selects the best card.
* If all cards perform 0 on the tests, it selects a random card.