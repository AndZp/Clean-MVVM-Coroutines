package mobi.mateam.core.utils



import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.max

private val timeConverter = TimeConverter()

/***** Long Extensions *****/

fun Long.toEventListDate(): String {
    return timeConverter.toDate(this)
}

fun Long.toTopMomentListDate(): String {
    return timeConverter.toDateWithYear(this)
}

fun Long.toEventListTime(): String {
    return timeConverter.toTime(this)
}

fun Long.toHumanReadableTime(withDate: Boolean = false): String {
    return timeConverter.toHumanDate(this, withDate)
}

fun Long.toMinutesSecondsForm(leadingZero: Boolean = true): String {
    val minutes = max(TimeUnit.SECONDS.toMinutes(this).toInt(), 0)
    val seconds = max((this - TimeUnit.MINUTES.toSeconds(minutes.toLong())).toInt(), 0)
    val stringFormat = if (leadingZero) "%02d:%02d" else "%d:%02d"
    return String.format(Locale.getDefault(), stringFormat, minutes, seconds)
}

fun Long.toHoursMinutesSecondsForm(): String {
    val hours = TimeUnit.SECONDS.toHours(this)
    val minutes = TimeUnit.SECONDS.toMinutes(this - TimeUnit.HOURS.toSeconds(hours))
    val seconds = this - TimeUnit.HOURS.toSeconds(hours) - TimeUnit.MINUTES.toSeconds(minutes)
    return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
}

object TimeUtils {

    private val msSimpleDateFormat: SimpleDateFormat = SimpleDateFormat.getDateTimeInstance() as SimpleDateFormat

    fun currentTrueTimeInMilliseconds(): Long {
        return System.currentTimeMillis()
    }


    fun periodTimeLeft(timeLeft: Long): String {
        msSimpleDateFormat.applyPattern(TimePattern.M_SS.timeFormat)
        return msSimpleDateFormat.format(Date(timeLeft))
    }

    fun getTimeUntil(time: Long): Long {
        return time - currentTrueTimeInMilliseconds()
    }

    fun eventTimeLog(time: Long): String {
        val simpleDateFormat = SimpleDateFormat("HH:mm:ss:SSS dd/MM/yyyy", Locale.getDefault())
        return try {
            simpleDateFormat.format(Date(time))
        } catch (e: Exception) {
            Date(time).toString()
        }

    }

    fun momentMatchDate(time: Long): String {
        val simpleDateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return try {
            simpleDateFormat.format(Date(time))
        } catch (e: Exception) {
            Date(time).toString()
        }
    }

    enum class TimePattern(val timeFormat: String) {
        /**
         * Tue, 18:07, Jul 11
         */
        EEE_HH_MM_MMM_DD("EEE, HH:mm, MMM dd"),

        /**
         * Tue, 6:07 pm, Jul 11
         */
        EEE_HH_MM_A_MMM_DD("EEE, h:mm a, MMM dd"),

        /**
         * 11 Jul 2017 18:07
         */
        DD_MMM_YYYY_HH_MM("dd MMM yyyy HH:mm"),

        /**
         * 11 Jul 2017 6:07 pm
         */
        DD_MMM_YYYY_HH_MM_A("dd MMM yyyy h:mm a"),

        /**
         * 11 Jul, 18:07
         */
        DD_MMM_HH_MM("dd MMM, HH:mm"),

        /**
         * 11 Jul, 6:07 pm
         */
        DD_MMM_HH_MM_A("dd MMM, h:mm a"),

        /**
         * 18:07
         */
        HH_MM_24_HOURS_FORMAT("HH:mm"),

        /**
         * 18:07:100
         */

        HH_MM_SS_SSS("HH:mm:ss:SSS"),

        /**
         * 6:07AM
         */
        HH_MM_12_HOURS_FORMAT("h:mmaa"),

        /**
         * Jul 11
         */
        MMM_DD("MMM dd"),

        /**
         * Tue, Jul 11
         */
        EEE_MMM_DD("EEE, MMM dd"),

        /**
         * 11/07
         */
        DD_MM("dd/MM"),

        /**
         * Tue
         */
        EEE("EEE"),

        /**
         * Jul
         */
        MMM("MMM"),

        /**
         * 11/07/2017
         */
        HH_MM_SS("dd/MM/yyyy"),

        /**
         * 04:08:59
         */
        DD_MM_YYYY("hh:mm:ss"),

        /**
         * 08:59
         */
        MM_SS("mm:ss"),

        /**
         * 8:59
         */
        M_SS("m:ss")
    }
}

class TimeConverter
{
    fun toDate(time: Long): String
    {
        val sdf = SimpleDateFormat("dd MMM", Locale.getDefault())
        return sdf.format(time)
    }

    fun toDateWithYear(time: Long): String
    {
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return sdf.format(time)
    }

    fun toTime(time: Long): String
    {
        val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
        return sdf.format(time)
    }

    fun toHumanDate(time: Long, withDate: Boolean = false): String
    {
        val dateFormat = "dd/MM/yyyy - "
        val timeFormat = "HH:mm:ss:SSS"

        val format: String = if (withDate) dateFormat + timeFormat else timeFormat
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(time)
    }
}