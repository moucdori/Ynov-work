import random

both_alive = True
tiles = {}
props = {}

for index in range(0, 20):
    tiles[index] = random.randint(-100, 50)
    props[index] = 0
    print(tiles[index])


class Player:
    def __init__(self, name, balance, cards_owned, current_pos, bankruptcy_status):
        self.name = name                            # str
        self.balance = balance                      # int
        self.cards_owned = cards_owned              # list
        self.current_pos = current_pos              # int (index)
        self.bankruptcy_status = bankruptcy_status  # bool

    def roll(self):
        d1 = random.randint(1, 6)
        d2 = random.randint(1, 6)
        d = d1 + d2
        print(f"{self.name} threw {d}")
        return d

    def move_player(self, dice_amt):
        self.current_pos += dice_amt
        return self.current_pos

    def add_balance(self, amount):
        self.balance += amount
        return self.balance

    def display_player_properties(self):
        total = 0
        for card in self.cards_owned:
            print(f"{card.card_name}: ${card.card_cost}")
            total += card.card_cost
        print(f"The sum of your card costs is: ${total}")

class Card:
    def __init__(self, card_cost,rent_prices,owner,value):
        self.card_cost = card_cost
        self.rent_prices = rent_prices
        self.owner = owner
        self.value = value

    def purchase_card(self, player):
        if self.card_cost > player.balance:
            print("You cannot afford this card at the moment.")
        else:
            player.cards_owned.append(self)
            player.reduce_balance(self.card_cost)
            self.owner = player

    def sell(self, player):
        player.add_balance(self.card_cost)
        self.owner = 'Bank'


p1_money = 2000
p1_place = 0
p2_money = 2000
p2_place = 0

def throw_dice():
    return (random.randint(1,6) + random.randint(1,6))

while both_alive is True:

    print("It's Player1's turn.")
    p1_place = (p1_place + throw_dice()) % 20
    print("Turn result : Player1 go to tiles N°" + str(p1_place))
    print("Tile bonus or minus : " + str(tiles[p1_place]))
    p1_money += tiles[p1_place]
    print("Player1's money after update : " + str(p1_money) + "\n")
    print(f"You can buy a property for {abs(tiles[p1_place]*4)}, do you want to ? 1 for yes, 2 for no.")
    inpt = input()
    if inpt == 1:
        p1_money = p1_money - (tiles[p1_place]*4)
        props[p1_place] = "Player1"
    else:
        pass
    print(f"Player1 money after payment : {p1_money}.")
    print(f"Player1 money after payment : {p1_money}.")

    if p1_money < 0 or p2_money < 0:
        print("Player1 fin: " + str(p1_money))
        print("Player2 fin: " + str(p2_money))
        both_alive = False
        break


    print("It's Player2's turn.")
    p2_place = (p2_place + throw_dice()) % 20
    print("Turn result : Player2 go to tiles N°" + str(p2_place))
    print("Tile bonus or minus : " + str(tiles[p2_place]))
    p2_money += tiles[p2_place]
    print("Player2's money after update : " + str(p2_money) + "\n")


    if p1_money < 0 or p2_money < 0:
        print("Player1 fin: " + str(p1_money))
        print("Player2 fin: " + str(p2_money))
        both_alive = False
        break
