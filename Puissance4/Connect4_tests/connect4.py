import numpy as np
import pygame
import sys
import math
import random
from os import path
import pickle

epsilon = 0.9
lr_rate = 0.81
gamma = 0.9

BLUE = (0,0,255)
BLACK = (0,0,0)
RED = (255,0,0)
YELLOW = (255,255,0)

ROW_COUNT = 6
COLUMN_COUNT = 7

#PLAYER = 0
AGENT1 = 0
AGENT2 = 1
#PLAYER_PIECE = 1
AGENT1_PIECE = 1
AGENT2_PIECE = 2

winsA1 = 0
winsA2 = 0

Q = {} # Format : '[[State]]' : [Values]

def choose_action(state):
	action = 0
	if (np.random.uniform(0, 1) < epsilon) or state not in Q:
		action = random.randint(0, 6)
	else:
		action = np.argmax(Q[state])
	return action

def learn(state, state2, reward, action):
	if state not in Q:
		Q[state] = [0,0,0,0,0,0,0]
	newValues = Q[state]
	if state2 not in Q:
		Q[state2] = [0,0,0,0,0,0,0]
	s2Values = Q[state2]
	newValues[action] += lr_rate * (reward + gamma * s2Values[np.argmax(Q[state2])]) + (1 - lr_rate) * newValues[action]
	Q[state] = newValues

def getReward(action):
	reward = 0
	if game_over:
		reward = 10
	return reward

def saveProgress(obj, name):
    with open(name + '.pkl', 'wb') as f:
        pickle.dump(obj, f, pickle.HIGHEST_PROTOCOL)

def loadProgress(name):
    with open(name + '.pkl', 'rb') as f:
        return pickle.load(f)

#def saveProgress():
#	np.save('p4progress', Q)
#
#def loadProgress():
#	Q = np.load('p4progress.npy')


def create_board():
	board = np.zeros((ROW_COUNT,COLUMN_COUNT))
	return board

def drop_piece(board, row, col, piece):
	board[row][col] = piece

def is_valid_location(board, col):
	return board[ROW_COUNT-1][col] == 0

def get_next_open_row(board, col):
	for r in range(ROW_COUNT):
		if board[r][col] == 0:
			return r

def print_board(board):
	print(np.flip(board, 0))

def winning_move(board, piece):
	# Check horizontal locations for win
	for c in range(COLUMN_COUNT-3):
		for r in range(ROW_COUNT):
			if board[r][c] == piece and board[r][c+1] == piece and board[r][c+2] == piece and board[r][c+3] == piece:
				return True

	# Check vertical locations for win
	for c in range(COLUMN_COUNT):
		for r in range(ROW_COUNT-3):
			if board[r][c] == piece and board[r+1][c] == piece and board[r+2][c] == piece and board[r+3][c] == piece:
				return True

	# Check positively sloped diaganols
	for c in range(COLUMN_COUNT-3):
		for r in range(ROW_COUNT-3):
			if board[r][c] == piece and board[r+1][c+1] == piece and board[r+2][c+2] == piece and board[r+3][c+3] == piece:
				return True

	# Check negatively sloped diaganols
	for c in range(COLUMN_COUNT-3):
		for r in range(3, ROW_COUNT):
			if board[r][c] == piece and board[r-1][c+1] == piece and board[r-2][c+2] == piece and board[r-3][c+3] == piece:
				return True

def draw_board(board):
	for c in range(COLUMN_COUNT):
		for r in range(ROW_COUNT):
			pygame.draw.rect(screen, BLUE, (c*SQUARESIZE, r*SQUARESIZE+SQUARESIZE, SQUARESIZE, SQUARESIZE))
			pygame.draw.circle(screen, BLACK, (int(c*SQUARESIZE+SQUARESIZE/2), int(r*SQUARESIZE+SQUARESIZE+SQUARESIZE/2)), RADIUS)
	
	for c in range(COLUMN_COUNT):
		for r in range(ROW_COUNT):		
			if board[r][c] == 1:
				pygame.draw.circle(screen, RED, (int(c*SQUARESIZE+SQUARESIZE/2), height-int(r*SQUARESIZE+SQUARESIZE/2)), RADIUS)
			elif board[r][c] == 2: 
				pygame.draw.circle(screen, YELLOW, (int(c*SQUARESIZE+SQUARESIZE/2), height-int(r*SQUARESIZE+SQUARESIZE/2)), RADIUS)
	pygame.display.update()

for i in range(1000):

	board = create_board()
	print_board(board)
	game_over = False
	turn = 0
	pieces_played = 0

	pygame.init()

	SQUARESIZE = 100

	width = COLUMN_COUNT * SQUARESIZE
	height = (ROW_COUNT+1) * SQUARESIZE

	size = (width, height)

	RADIUS = int(SQUARESIZE/2 - 5)

	screen = pygame.display.set_mode(size)
	draw_board(board)
	pygame.display.update()

	myfont = pygame.font.SysFont("monospace", 75)


	if path.isfile('p4progress.pkl'):
		Q = loadProgress('p4progress')

	while not game_over:

		#for event in pygame.event.get():
		#	if event.type == pygame.QUIT:
		#		sys.exit()
	#
		#	if event.type == pygame.MOUSEMOTION:
		#		pygame.draw.rect(screen, BLACK, (0,0, width, SQUARESIZE))
		#		posx = event.pos[0]
		#		if turn == PLAYER:
		#			pygame.draw.circle(screen, RED, (posx, int(SQUARESIZE/2)), RADIUS)
	#
		#	pygame.display.update()
	#
		#	if event.type == pygame.MOUSEBUTTONDOWN:
		#		pygame.draw.rect(screen, BLACK, (0,0, width, SQUARESIZE))
		#		#print(event.pos)
		#		# Ask for Player 1 Input
		#		if turn == PLAYER:
		#			posx = event.pos[0]
		#			col = int(math.floor(posx/SQUARESIZE))
	#
		#			if is_valid_location(board, col):
		#				row = get_next_open_row(board, col)
		#				drop_piece(board, row, col, PLAYER_PIECE)
	#
		#				if winning_move(board, PLAYER_PIECE):
		#					label = myfont.render("Player 1 wins!!", 1, RED)
		#					screen.blit(label, (40,10))
		#					game_over = True
	#
		#				turn += 1
		#				turn = turn % 2
	#
		#				print_board(board)
		#				draw_board(board)


		# # Ask for Agent 1 Input
		if turn == AGENT1 and not game_over:
			state = str(board)
			col = choose_action(state)
			if is_valid_location(board, col):
				row = get_next_open_row(board, col)
				drop_piece(board, row, col, AGENT1_PIECE)
				pieces_played += 1

				if winning_move(board, AGENT1_PIECE):
					label = myfont.render("Player 1 wins!!", 1, RED)
					winsA1 += 1
					screen.blit(label, (40,10))
					game_over = True

				state2 = str(board)
				reward = getReward(col)
				learn(state, state2, reward, col)
				print_board(board)
				draw_board(board)
				if pieces_played == 42:
					game_over = True

				turn += 1
				turn = turn % 2


		# # Ask for Agent 2 Input
		if turn == AGENT2 and not game_over:
			state = str(board)
			col = choose_action(state)
			if is_valid_location(board, col):
				row = get_next_open_row(board, col)
				drop_piece(board, row, col, AGENT2_PIECE)
				pieces_played += 1

				if winning_move(board, AGENT2_PIECE):
					label = myfont.render("Player 2 wins!!", 1, YELLOW)
					winsA2 += 1
					screen.blit(label, (40,10))
					game_over = True

				state2 = str(board)
				reward = getReward(col)
				learn(state, state2, reward, col)
				print_board(board)
				draw_board(board)
				if pieces_played == 42:
					game_over = True

				turn += 1
				turn = turn % 2

		if game_over:
			saveProgress(Q, 'p4progress')

print("L'agent 1 (RED) a gagné " + str(winsA1) + " fois\n")
print("L'agent 2 (YELLOW) a gagné " + str(winsA2) + " fois\n")