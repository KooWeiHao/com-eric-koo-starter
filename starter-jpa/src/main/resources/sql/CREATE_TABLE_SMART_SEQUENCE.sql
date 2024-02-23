# TODO: Create automation to generate the script
CREATE TABLE IF NOT EXISTS `smart_sequence`
(
    `id`       bigint     NOT NULL AUTO_INCREMENT,
    `for_date` date       NULL,
    `prefix`   varchar(5) NOT NULL,
    `sequence` int        NULL,
    PRIMARY KEY (`id`)
);