package com.chs.youranimelist.network.dto

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class AniList (
    @SerializedName("data")
    val `data`: Data
)

data class Data (
    @SerializedName("Page")
    val page: Page
)

data class Page (
    @SerializedName("media")
    val media: List<Anime>
)

data class Anime(
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("idMal")
    val idMal: String? = "",
    @SerializedName("title")
    val title: Title?,
    @SerializedName("description")
    val description: String? = "Test",
    @SerializedName("format")
    val format: String? = "",
    @SerializedName("trailer")
    val trailer: Trailer?,
    @SerializedName("episode")
    val episode: String? = "",
    @SerializedName("coverImage")
    val coverImage: CoverImage?,
    @SerializedName("bannerImage")
    val bannerImage: String? = "",
    @SerializedName("genres")
    val genres: ArrayList<String>?,
    @SerializedName("status")
    val status: String? = "",
    @SerializedName("seasonYear")
    val seasonYear: String? = "",
    @SerializedName("popularity")
    val popularity: String? = "",
    @SerializedName("favourites")
    val favourites: String? = "",
    @SerializedName("averageScore")
    val averageScore: String? = "",
    @SerializedName("meanScore")
    val meanScore: String? = "",
    @SerializedName("studios")
    val studios: Studios?,
    @SerializedName("source")
    val source: String? = "",
    @SerializedName("recommendations")
    val recommendations: Recommendations?,

    ):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable<Title>(Title.javaClass.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable<Trailer>(Trailer::class.java.classLoader),
        parcel.readString(),
        parcel.readParcelable<CoverImage>(CoverImage::class.java.classLoader),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable<Studios>(Studios::class.java.classLoader),
        parcel.readString(),
        parcel.readParcelable<Recommendations>(Recommendations::class.java.classLoader),
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(id)
        dest?.writeString(idMal)
        dest?.writeParcelable(title,flags)
        dest?.writeString(description)
        dest?.writeString(format)
        dest?.writeParcelable(trailer,flags)
        dest?.writeString(episode)
        dest?.writeParcelable(coverImage,flags)
        dest?.writeString(bannerImage)
        dest?.writeList(genres)
        dest?.writeString(status)
        dest?.writeString(seasonYear)
        dest?.writeString(popularity)
        dest?.writeString(favourites)
        dest?.writeString(averageScore)
        dest?.writeString(meanScore)
        dest?.writeParcelable(studios,flags)
        dest?.writeString(source)
        dest?.writeParcelable(recommendations,flags)
    }

    companion object CREATOR : Parcelable.Creator<Anime> {
        override fun createFromParcel(parcel: Parcel): Anime {
            return Anime(parcel)
        }

        override fun newArray(size: Int): Array<Anime?> {
            return arrayOfNulls(size)
        }
    }
}

data class Title (
    @SerializedName("romaji")
    val romaji: String? = "",
    @SerializedName("english")
    val english: String? = "",
    @SerializedName("native")
    val native: String? = ""
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(romaji)
        dest?.writeString(english)
        dest?.writeString(native)
    }

    companion object CREATOR : Parcelable.Creator<Title> {
        override fun createFromParcel(parcel: Parcel): Title {
            return Title(parcel)
        }

        override fun newArray(size: Int): Array<Title?> {
            return arrayOfNulls(size)
        }
    }
}

data class Trailer (
    @SerializedName("id")
    val id: String?,
    @SerializedName("thumbnail")
    val thumbnail: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(id)
        dest?.writeString(thumbnail)
    }

    companion object CREATOR : Parcelable.Creator<Trailer> {
        override fun createFromParcel(parcel: Parcel): Trailer {
            return Trailer(parcel)
        }

        override fun newArray(size: Int): Array<Trailer?> {
            return arrayOfNulls(size)
        }
    }
}

data class CoverImage (
    @SerializedName("extraLarge")
    val extraLarge: String? = "",
    @SerializedName("color")
    val color: String? = ""
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(extraLarge)
        dest?.writeString(color)
    }

    companion object CREATOR : Parcelable.Creator<CoverImage> {
        override fun createFromParcel(parcel: Parcel): CoverImage {
            return CoverImage(parcel)
        }

        override fun newArray(size: Int): Array<CoverImage?> {
            return arrayOfNulls(size)
        }
    }
}

data class Recommendations (
    @SerializedName("node")
    val recommendationsNode: List<AnimeRecommendation>?
):Parcelable {
    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(AnimeRecommendation)
    )

    override fun describeContents(): Int {
        return 0
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeTypedList(recommendationsNode)
    }

    companion object CREATOR : Parcelable.Creator<Recommendations> {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun createFromParcel(parcel: Parcel): Recommendations {
            return Recommendations(parcel)
        }

        override fun newArray(size: Int): Array<Recommendations?> {
            return arrayOfNulls(size)
        }
    }
}

data class AnimeRecommendation (
    @SerializedName("id")
    val id: String?="",
    @SerializedName("idMal")
    val idMal: String?="",
    @SerializedName("title")
    val title:Title?,
    @SerializedName("coverImage")
    val coverImage: CoverImage?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Title::class.java.classLoader),
        parcel.readParcelable(CoverImage::class.java.classLoader)
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(idMal)
        parcel.writeParcelable(title, flags)
        parcel.writeParcelable(coverImage, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AnimeRecommendation> {
        override fun createFromParcel(parcel: Parcel): AnimeRecommendation {
            return AnimeRecommendation(parcel)
        }

        override fun newArray(size: Int): Array<AnimeRecommendation?> {
            return arrayOfNulls(size)
        }
    }
}

data class Studios (
    @SerializedName("node")
    val studiosNode: List<StudioConnection>?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(StudioConnection))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(studiosNode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Studios> {
        override fun createFromParcel(parcel: Parcel): Studios {
            return Studios(parcel)
        }

        override fun newArray(size: Int): Array<Studios?> {
            return arrayOfNulls(size)
        }
    }
}

data class StudioConnection (
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("name")
    val name: String? = ""
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StudioConnection> {
        override fun createFromParcel(parcel: Parcel): StudioConnection {
            return StudioConnection(parcel)
        }

        override fun newArray(size: Int): Array<StudioConnection?> {
            return arrayOfNulls(size)
        }
    }
}

