CREATE SCHEMA IF NOT EXISTS video;

SET SCHEMA video;

CREATE TABLE IF NOT EXISTS channel_result (
	id IDENTITY PRIMARY KEY,
	channel_id VARCHAR(100) NOT NULL,
	search_etag VARCHAR(50) NOT NULL,
	items CLOB NOT NULL,
	created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	UNIQUE(channel_id)
);

CREATE INDEX idx_updated_on_desc ON channel_result (updated_on DESC);


INSERT INTO channel_result(channel_id, search_etag, items) VALUES (
	'UCYzPXprvl5Y-Sf0g4vX-m6g',
	'qf_eRDTon-cmGLbUJWmAwALKbkc',
	'[
		{
			"kind": "youtube#searchResult",
			"etag": "NFk1anejiMl0ESUk46srMXMBMcQ",
			"id": {
				"kind": "youtube#video",
				"videoId": "FiNRLfZkkZk"
			},
			"snippet": {
				"publishedAt": "2025-01-31T22:19:06Z",
				"channelId": "UCYzPXprvl5Y-Sf0g4vX-m6g",
				"title": "Poppy Playtime Chapter 4 (FULL GAME)",
				"description": "Poppy Playtime Chapter 4 is here and I''ve got the full game for you to watch Buy MY coffee: https://topofthemornincoffee.com/ ...",
				"thumbnails": {
					"default": {
						"url": "https://i.ytimg.com/vi/FiNRLfZkkZk/default.jpg",
						"width": 120,
						"height": 90
					},
					"medium": {
						"url": "https://i.ytimg.com/vi/FiNRLfZkkZk/mqdefault.jpg",
						"width": 320,
						"height": 180
					},
					"high": {
						"url": "https://i.ytimg.com/vi/FiNRLfZkkZk/hqdefault.jpg",
						"width": 480,
						"height": 360
					}
				},
				"channelTitle": "jacksepticeye",
				"liveBroadcastContent": "none",
				"publishTime": "2025-01-31T22:19:06Z"
			}
		},
		{
			"kind": "youtube#searchResult",
			"etag": "Mxh51ukpUif6pf5NE66dTH6eq7s",
			"id": {
				"kind": "youtube#video",
				"videoId": "-eVOxb2KQSI"
			},
			"snippet": {
				"publishedAt": "2025-01-28T17:00:00Z",
				"channelId": "UCYzPXprvl5Y-Sf0g4vX-m6g",
				"title": "WE ARE SO BACK - Turbo Dismount 2",
				"description": "It''s time to go all the way back to 2014 because Turbo Dismount 2 is here! Buy MY coffee: https://topofthemornincoffee.com/ Edited ...",
				"thumbnails": {
					"default": {
						"url": "https://i.ytimg.com/vi/-eVOxb2KQSI/default.jpg",
						"width": 120,
						"height": 90
					},
					"medium": {
						"url": "https://i.ytimg.com/vi/-eVOxb2KQSI/mqdefault.jpg",
						"width": 320,
						"height": 180
					},
					"high": {
						"url": "https://i.ytimg.com/vi/-eVOxb2KQSI/hqdefault.jpg",
						"width": 480,
						"height": 360
					}
				},
				"channelTitle": "jacksepticeye",
				"liveBroadcastContent": "none",
				"publishTime": "2025-01-28T17:00:00Z"
			}
		},
		{
			"kind": "youtube#searchResult",
			"etag": "GZNUhoMZNcjIA0x85zref-Ctn2c",
			"id": {
				"kind": "youtube#video",
				"videoId": "m-AQj_LlDv4"
			},
			"snippet": {
				"publishedAt": "2025-01-24T17:00:56Z",
				"channelId": "UCYzPXprvl5Y-Sf0g4vX-m6g",
				"title": "Trapped Underground With No Way Out (Amenti)",
				"description": "What would you do if you were trapped underground with no way out? Buy MY coffee: https://topofthemornincoffee.com/ Edited By: ...",
				"thumbnails": {
					"default": {
						"url": "https://i.ytimg.com/vi/m-AQj_LlDv4/default.jpg",
						"width": 120,
						"height": 90
					},
					"medium": {
						"url": "https://i.ytimg.com/vi/m-AQj_LlDv4/mqdefault.jpg",
						"width": 320,
						"height": 180
					},
					"high": {
						"url": "https://i.ytimg.com/vi/m-AQj_LlDv4/hqdefault.jpg",
						"width": 480,
						"height": 360
					}
				},
				"channelTitle": "jacksepticeye",
				"liveBroadcastContent": "none",
				"publishTime": "2025-01-24T17:00:56Z"
			}
		},
		{
			"kind": "youtube#searchResult",
			"etag": "ZJzNvqsmdhq6KwqXYUhaOKoW7AY",
			"id": {
				"kind": "youtube#video",
				"videoId": "swxAd0yR8Lc"
			},
			"snippet": {
				"publishedAt": "2025-01-22T16:09:36Z",
				"channelId": "UCYzPXprvl5Y-Sf0g4vX-m6g",
				"title": "I Can&#39;t Believe These Ads Are Allowed On TikTok",
				"description": "I found the most ridiculous ads on TikTok Buy MY coffee: https://topofthemornincoffee.com/ Edited By: ...",
				"thumbnails": {
					"default": {
						"url": "https://i.ytimg.com/vi/swxAd0yR8Lc/default.jpg",
						"width": 120,
						"height": 90
					},
					"medium": {
						"url": "https://i.ytimg.com/vi/swxAd0yR8Lc/mqdefault.jpg",
						"width": 320,
						"height": 180
					},
					"high": {
						"url": "https://i.ytimg.com/vi/swxAd0yR8Lc/hqdefault.jpg",
						"width": 480,
						"height": 360
					}
				},
				"channelTitle": "jacksepticeye",
				"liveBroadcastContent": "none",
				"publishTime": "2025-01-22T16:09:36Z"
			}
		},
		{
			"kind": "youtube#searchResult",
			"etag": "biJUQ1oidSso5vLlphxW17l9TIM",
			"id": {
				"kind": "youtube#video",
				"videoId": "o-7-dl_KRys"
			},
			"snippet": {
				"publishedAt": "2022-08-04T17:39:48Z",
				"channelId": "UCYzPXprvl5Y-Sf0g4vX-m6g",
				"title": "The Mortuary Assistant (FULL GAME)",
				"description": "The Mortuary Assistant''s full version is finally out. let''s get scared Buy MY coffee: https://topofthemornincoffee.com/ Twitter ...",
				"thumbnails": {
					"default": {
						"url": "https://i.ytimg.com/vi/o-7-dl_KRys/default.jpg",
						"width": 120,
						"height": 90
					},
					"medium": {
						"url": "https://i.ytimg.com/vi/o-7-dl_KRys/mqdefault.jpg",
						"width": 320,
						"height": 180
					},
					"high": {
						"url": "https://i.ytimg.com/vi/o-7-dl_KRys/hqdefault.jpg",
						"width": 480,
						"height": 360
					}
				},
				"channelTitle": "jacksepticeye",
				"liveBroadcastContent": "none",
				"publishTime": "2022-08-04T17:39:48Z"
			}
		}
	]'	
);