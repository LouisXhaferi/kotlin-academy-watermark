abstract class Positioning {
    companion object {
        fun cover() = CoverPositioning()
        fun grid() = GridPositioning()
        fun coordinate(x: Int, y: Int) = CoordinatePositioning(x, y)
    }
}

class CoverPositioning : Positioning()
class GridPositioning : Positioning()
class CoordinatePositioning(val x: Int, val y: Int) : Positioning()
