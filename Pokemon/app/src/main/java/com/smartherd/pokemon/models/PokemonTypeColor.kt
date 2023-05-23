import android.content.Context
import androidx.core.content.ContextCompat
import com.smartherd.pokemon.R

enum class PokemonTypeColor(private val colorResId: Int) {
    FIRE(R.color.fire),
    WATER(R.color.water),
    GRASS(R.color.grass),
    ELECTRIC(R.color.electric),
    ICE(R.color.ice),
    FIGHTING(R.color.fighting),
    POISON(R.color.poison),
    GROUND(R.color.ground),
    FLYING(R.color.flying),
    PSYCHIC(R.color.psychic),
    BUG(R.color.bug),
    ROCK(R.color.rock),
    GHOST(R.color.ghost),
    DRAGON(R.color.dragon),
    DARK(R.color.dark),
    STEEL(R.color.steel),
    FAIRY(R.color.fairy),
    NORMAL(R.color.normal);

    fun getColor(context: Context): Int {
        return ContextCompat.getColor(context, colorResId)
    }

    companion object {
        fun getColor(typeName: String, context: Context): Int {
            return values().find { it.name.equals(typeName, ignoreCase = true) }?.getColor(context)
                ?: ContextCompat.getColor(context, NORMAL.colorResId)
        }
    }
}
