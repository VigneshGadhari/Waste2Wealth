package app.waste2wealth.com.tags

import app.waste2wealth.com.R

data class Tag(
    val name: String,
    val image: Int,
    val tips: List<String> = emptyList()
) {
    constructor() : this("", 0, emptyList())

    fun mapWithoutTips() = TagWithoutTips(
        name = name,
        image = image
    )
}

data class TagWithoutTips(
    val name: String,
    val image: Int,
) {
    constructor() : this("", 0)
}

data class Groups(
    val name: String,
    val tags: List<Tag> = emptyList()
) {
    constructor() : this("", emptyList())

    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            name,
            "${name.first()} ${tags.joinToString(" ") { it.name }}",
            "${tags.any()}",
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}


val wasteGroups = listOf(
    Groups(
        name = "Plastic Waste",
        tags = listOf(
            Tag(
                name = "Plastic Bottles",
                tips = listOf(
                    "Recycle plastic bottles to reduce environmental impact.",
                    "Use reusable water bottles to minimize plastic bottle waste.",
                    "Dispose of plastic bottles properly in recycling bins.",
                    "Consider upcycling plastic bottles for DIY projects.",
                    "Avoid single-use plastic bottles whenever possible."
                ),
                image = R.drawable.plastic_bottle
            ),
            Tag(
                name = "Plastic Bags",
                tips = listOf(
                    "Use reusable shopping bags to reduce plastic bag waste.",
                    "Recycle plastic bags at designated collection points.",
                    "Reduce plastic bag usage by opting for paper or cloth bags.",
                    "Avoid littering plastic bags to protect the environment.",
                    "Consider repurposing plastic bags for storage or crafts."
                ),
                image = R.drawable.plastic_bag

            ),
            Tag(
                name = "Plastic Containers",
                tips = listOf(
                    "Recycle plastic containers with the recycling number on the bottom.",
                    "Avoid microwaving food in plastic containers to prevent chemical leaching.",
                    "Consider using glass or stainless steel containers for food storage.",
                    "Repurpose plastic containers for organizing small items.",
                    "Support businesses that offer plastic container recycling programs."
                ),
                image = R.drawable.plastic_container
            ),
            Tag(
                name = "Plastic Wrappers",
                tips = listOf(
                    "Reduce plastic wrapper waste by choosing products with minimal packaging.",
                    "Recycle plastic wrappers at specialized collection points.",
                    "Opt for reusable beeswax wraps for food storage.",
                    "Avoid buying individually wrapped items.",
                    "Participate in plastic wrapper recycling initiatives in your community."
                ),
                image = R.drawable.plastic_wrapper


            ),
            Tag(
                name = "Plastic Toys",
                tips = listOf(
                    "Donate unwanted plastic toys to charity organizations.",
                    "Recycle broken plastic toys if possible.",
                    "Encourage children to take care of their plastic toys to extend their lifespan.",
                    "Avoid buying excessive plastic toys to reduce waste.",
                    "Look for eco-friendly and sustainable toy options made from biodegradable materials."
                ),
                image = R.drawable.plastic_toys
            )
        )
    ),
    Groups(
        name = "Paper Waste",
        tags = listOf(
            Tag(
                name = "Newspapers",
                tips = listOf(
                    "Recycle newspapers to save trees and energy.",
                    "Reuse old newspapers for wrapping or crafting.",
                    "Avoid throwing newspapers in the trash; recycle them instead.",
                    "Support digital news platforms to reduce paper waste.",
                    "Consider donating newspapers to animal shelters for bedding."
                ),
                image = R.drawable.paper_newspaper
            ),
            Tag(
                name = "Cardboard",
                tips = listOf(
                    "Flatten and recycle cardboard boxes to save space in landfills.",
                    "Use cardboard for DIY projects and crafts.",
                    "Reuse cardboard packaging for storage or shipping.",
                    "Recycle cardboard packaging from online shopping.",
                    "Support businesses that use eco-friendly cardboard packaging."
                ),
                image = R.drawable.paper_cardboard
            ),
            Tag(
                name = "Office Paper",
                tips = listOf(
                    "Recycle office paper to reduce paper waste in workplaces.",
                    "Use both sides of office paper for printing and note-taking.",
                    "Implement digital document management systems to reduce paper usage.",
                    "Encourage employees to recycle paper and use electronic communication.",
                    "Choose recycled office paper products to support sustainability."
                ),
                image = R.drawable.paper_office
            ),
        )
    ),
    Groups(
        name = "Glass Waste",
        tags = listOf(
            Tag(
                name = "Glass Bottles",
                tips = listOf(
                    "Recycle glass bottles to conserve resources and energy.",
                    "Reuse glass bottles for homemade beverages or storage.",
                    "Dispose of broken glass bottles safely to prevent injuries.",
                    "Support bottle return programs to promote glass recycling.",
                    "Choose products with glass packaging to reduce waste."
                ),
                image = R.drawable.glass_bottle
            ),
            Tag(
                name = "Broken Glass",
                tips = listOf(
                    "Handle broken glass with care to avoid injuries.",
                    "Safely dispose of broken glass in a puncture-proof container.",
                    "Label containers with broken glass to alert waste collectors.",
                    "Consider recycling broken glass at designated collection points.",
                    "Educate others about safe handling and disposal of broken glass."
                ),
                image = R.drawable.glass_broken
            ),
            Tag(
                name = "Glassware",
                tips = listOf(
                    "Recycle glassware, such as drinking glasses and bowls.",
                    "Donate unwanted glassware to secondhand stores.",
                    "Repurpose glassware for creative DIY projects.",
                    "Avoid disposing of glassware in the trash.",
                    "Consider using durable glassware to reduce waste."
                ),
                image = R.drawable.glass_mirror
            )
        )
    ),
    Groups(
        name = "Metal Waste",
        tags = listOf(
            Tag(
                name = "Aluminum Cans",
                tips = listOf(
                    "Recycle aluminum cans to conserve energy and resources.",
                    "Crush cans to save space in recycling bins.",
                    "Avoid littering aluminum cans to protect the environment.",
                    "Consider collecting cans for recycling drives or charity.",
                    "Support metal recycling programs in your community."
                ),
                image = R.drawable.metal_aluminium
            ),
            Tag(
                name = "Steel Cans",
                tips = listOf(
                    "Recycle steel cans to reduce waste and save energy.",
                    "Rinse steel cans before recycling to prevent contamination.",
                    "Repurpose steel cans for organizing small items.",
                    "Support steel recycling initiatives in your area.",
                    "Choose products with minimal steel packaging to reduce waste."
                ),
                image = R.drawable.metal_steel_can
            ),
            Tag(
                name = "Scrap Metal",
                tips = listOf(
                    "Recycle scrap metal to prevent landfill waste.",
                    "Sort and separate different types of scrap metal for recycling.",
                    "Contact scrap metal yards for proper disposal and recycling.",
                    "Support businesses that buy and recycle scrap metal.",
                    "Avoid leaving scrap metal in public areas."
                ),
                image = R.drawable.metal_scrap
            ),

            // Add more tags and tips for Metal Waste...
        )
    ),
    Groups(
        name = "E-Waste",
        tags = listOf(
            Tag(
                name = "Smartphones",
                tips = listOf(
                    "Recycle old smartphones to recover valuable materials.",
                    "Donate functional smartphones to charitable organizations.",
                    "Erase personal data before disposing of smartphones.",
                    "Consider trading in old smartphones for discounts on new ones.",
                    "Support e-waste recycling programs in your region."
                ),
                image = R.drawable.e_mobile
            ),
            Tag(
                name = "Computer Accessories",
                tips = listOf(
                    "Recycle computer accessories like keyboards, mice, and cables.",
                    "Donate functional computer accessories to charities or schools.",
                    "Properly dispose of non-functional computer accessories.",
                    "Support e-waste collection events in your area.",
                    "Avoid storing unused computer accessories indefinitely."
                ),
                image = R.drawable.e_computer
            ),
            Tag(
                name = "Printers",
                tips = listOf(
                    "Recycle old printers to prevent e-waste buildup.",
                    "Donate functional printers to organizations or schools.",
                    "Dispose of non-functional printers responsibly.",
                    "Support e-waste recycling initiatives in your region.",
                    "Minimize unnecessary printing to reduce printer waste."
                ),
                image = R.drawable.e_printer
            )
            // Add more tags and tips for E-Waste...
        )
    ),
    Groups(
        name = "Organic Waste",
        tags = listOf(
            Tag(
                name = "Food Scraps",
                tips = listOf(
                    "Compost food scraps to create nutrient-rich soil.",
                    "Use a compost bin or pile for food waste decomposition.",
                    "Avoid composting meat and dairy products to prevent odors.",
                    "Learn about proper composting techniques for best results.",
                    "Support community composting programs."
                ),
                image = R.drawable.or_scraps
            ),
            Tag(
                name = "Garden Waste",
                tips = listOf(
                    "Use garden waste for mulching or composting.",
                    "Create a dedicated area for garden waste disposal.",
                    "Avoid disposing of garden waste in landfills.",
                    "Consider turning garden waste into organic fertilizer.",
                    "Support green waste collection services in your area."
                ),
                image = R.drawable.or_garden
            ),
            Tag(
                name = "Compost",
                tips = listOf(
                    "Compost fruit peels and skins to reduce waste.",
                    "Use citrus peels for natural cleaning solutions.",
                    "Repurpose fruit peels for homemade potpourri.",
                    "Avoid throwing fruit peels in the trash.",
                    "Support eco-friendly kitchen practices."
                ),
                image = R.drawable.or_compost
            ),
        )
    ),
    Groups(
        name = "Textile Waste",
        tags = listOf(
            Tag(
                name = "Clothing",
                tips = listOf(
                    "Donate gently used clothing to charities or shelters.",
                    "Host clothing swap events with friends and family.",
                    "Recycle worn-out clothing through textile recycling programs.",
                    "Consider upcycling old clothing into new fashion pieces.",
                    "Support sustainable fashion brands that promote recycling."
                ),
                image = R.drawable.textile_clothing
            ),
            Tag(
                name = "Footwear",
                tips = listOf(
                    "Donate or recycle old footwear to reduce waste.",
                    "Repair and refurbish worn-out shoes to extend their lifespan.",
                    "Avoid discarding shoes in landfills.",
                    "Support footwear donation drives for those in need.",
                    "Choose sustainable footwear options made from recycled materials."
                ),
                image = R.drawable.textile_shoes
            ),
            Tag(
                name = "Fabric Scraps",
                tips = listOf(
                    "Upcycle fabric scraps for sewing and crafting projects.",
                    "Donate excess fabric scraps to schools or community centers.",
                    "Recycle fabric scraps through textile recycling services.",
                    "Avoid adding fabric scraps to household waste.",
                    "Explore creative ways to repurpose fabric scraps in your home."
                ),
                image = R.drawable.textile_fabric
            )
            // Add more tags and tips for Textile Waste...
        )
    ),
    Groups(
        name = "Medical Waste",
        tags = listOf(
            Tag(
                name = "Needles and Syringes",
                tips = listOf(
                    "Dispose of used needles and syringes in designated sharps containers.",
                    "Never recap needles or syringes, and avoid bending or breaking them.",
                    "Check local regulations for proper disposal guidelines.",
                    "Avoid throwing sharps in the regular trash.",
                    "Support safe needle disposal programs in your community."
                ),
                image = R.drawable.medical_syringes
            ),
            Tag(
                name = "Expired Medications",
                tips = listOf(
                    "Return expired medications to pharmacies or designated collection sites.",
                    "Do not flush medications down the toilet or drain.",
                    "Follow disposal instructions on medication labels.",
                    "Keep medications out of reach of children and pets.",
                    "Support medication take-back programs for safe disposal."
                ),
                image = R.drawable.medical_contaminated
            ),
            Tag(
                name = "Medical Equipment",
                tips = listOf(
                    "Donate functional medical equipment to organizations in need.",
                    "Dispose of broken or non-functional medical equipment responsibly.",
                    "Follow manufacturer instructions for equipment disposal.",
                    "Support medical equipment recycling programs in your region.",
                    "Avoid contributing to medical waste through careful equipment usage."
                ),
                image = R.drawable.medical_bandage
            )
            // Add more tags and tips for Medical Waste...
        )
    )
)
