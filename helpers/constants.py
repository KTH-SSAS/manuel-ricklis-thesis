import os
import enum

REWARD = 0
RANK = 1
STEP = 2


class LoopPhase(enum.Enum):
    TRAIN = 0,
    VAL = 1,
    TEST = 2


BEST_VAL_PERF = 0
BEST_VAL_LOSS = 0
PATIENCE_CNT = 0

BINARIES_PATH = os.path.join(os.path.dirname(__file__), os.pardir, 'models', 'binaries')
CHECKPOINTS_PATH = os.path.join(os.path.dirname(__file__), os.pardir, 'models', 'checkpoints')
DATA_DIR_PATH = os.path.join(os.path.dirname(__file__), os.pardir, 'data')
