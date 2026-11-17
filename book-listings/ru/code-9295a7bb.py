from collections import Counter
from typing import Callable


def summarize(read_events: Callable[[dict], dict],
              actor: str, start: str, end: str) -> dict:
    page = read_events({
        "actor": actor, "from": start, "to": end, "limit": 20
    })
    events = page["items"]
    if len(events) > 20:
        raise ValueError("Ответ превысил согласованный размер страницы")
    counts = Counter((e["action"], e["outcome"]) for e in events)
    return {
        "sampleSize": len(events),
        "hasMore": page["hasMore"],
        "eventIds": [e["id"] for e in events],
        "groups": [
            {"action": action, "outcome": outcome, "count": count}
            for (action, outcome), count in sorted(counts.items())
        ],
    }
