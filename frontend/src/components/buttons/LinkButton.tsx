import { Group, Text, UnstyledButton } from "@mantine/core";
import { forwardRef } from "react";
import { NavLink } from 'react-router-dom';
import { NavbarLink } from "../../types";

interface LinkButtonProps {
    link: NavbarLink,
}

const LinkButton = forwardRef<HTMLButtonElement, LinkButtonProps>(
    ({ link, ...others }: LinkButtonProps, ref) => (
        <UnstyledButton
            ref={ref}
            sx={(theme) => ({
            display: 'block',
            width: '100%',
            padding: theme.spacing.md,
            color: theme.colorScheme === 'dark' ? theme.colors.dark[0] : theme.black,
    
            '&:hover': {
                backgroundColor:
                theme.colorScheme === 'dark' ? theme.colors.dark[8] : theme.colors.gray[0],
            },
            })}
            {...others}
        >
            <Group>
                {link.icon} 
                <Text 
                    size="md" 
                    weight={600}
                    component={NavLink}
                    to={link.to}
                    sx={(theme) => ({
                        color: theme.colorScheme === 'dark' ? theme.colors.dark[0] : theme.black,
                        '&:hover': {
                            textDecoration: 'none',
                        }
                    })}
                >
                    {link.label}
                </Text>
            </Group>
        </UnstyledButton>
    )
  );

export default LinkButton;