export interface TimeZoneOption {
    label: string;
    value: string;
}

export const TIME_ZONES: TimeZoneOption[] = [
    { label: 'India Standard Time (Asia/Kolkata)', value: 'Asia/Kolkata' },
    { label: 'Dubai (Asia/Dubai)', value: 'Asia/Dubai' },
    { label: 'Singapore (Asia/Singapore)', value: 'Asia/Singapore' },
    { label: 'Kuala Lumpur (Asia/Kuala_Lumpur)', value: 'Asia/Kuala_Lumpur' },
    { label: 'Bangkok (Asia/Bangkok)', value: 'Asia/Bangkok' },
    { label: 'Tokyo (Asia/Tokyo)', value: 'Asia/Tokyo' },
    { label: 'London (Europe/London)', value: 'Europe/London' },
    { label: 'Berlin (Europe/Berlin)', value: 'Europe/Berlin' },
    { label: 'Paris (Europe/Paris)', value: 'Europe/Paris' },
    { label: 'New York (America/New_York)', value: 'America/New_York' },
    { label: 'Chicago (America/Chicago)', value: 'America/Chicago' },
    { label: 'Denver (America/Denver)', value: 'America/Denver' },
    { label: 'Los Angeles (America/Los_Angeles)', value: 'America/Los_Angeles' },
    { label: 'Sydney (Australia/Sydney)', value: 'Australia/Sydney' },
    { label: 'UTC', value: 'UTC' }
];