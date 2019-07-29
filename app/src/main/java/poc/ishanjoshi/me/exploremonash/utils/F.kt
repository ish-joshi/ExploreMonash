package poc.ishanjoshi.me.exploremonash.utils


fun getEndFrom(startAt: String) : String {

    val startInt = Integer.parseInt(startAt)
    return "${startInt + C.BLOCK_SIZE}"

}