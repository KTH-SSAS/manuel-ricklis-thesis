"""
    Contains constants shared across the project.
"""

import os
import enum

# feature descriptors
STEP_NAME = 0
ASSET_TYPE = 1
ASSET_NAME = 2
REWARD = 3
RANK = 4


# 3 different model training/eval phases used in train.py
class LoopPhase(enum.Enum):
    TRAIN = 0,
    VAL = 1,
    TEST = 2


class VisualizationType(enum.Enum):
    ATTENTION = 0,
    EMBEDDINGS = 1,
    ENTROPY = 2,


# writer = SummaryWriter()  # (tensorboard) writer will output to ./runs/ directory by default


# Global vars used for early stopping. After some number of epochs (as defined by the patience_period var) without any
# improvement on the validation dataset (measured via accuracy/micro-F1 metric), we'll break out from the training loop.
BEST_VAL_PERF = 0
BEST_VAL_LOSS = 0
PATIENCE_CNT = 0

CHECKPOINTS_PATH = os.path.join(os.path.dirname(__file__), os.pardir, 'models', 'checkpoints')


#
# AttackGraph specific information
#

TRAIN_RANGE = [0, 140]
VAL_RANGE = [140, 140+500]
TEST_RANGE = [1708, 1708+1000]
NUM_INPUT_FEATURES = 5
NUM_CLASSES = 1